// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the math/vector/DenseXBufferVector.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.math.vector;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.FloatBuffer;
import java.util.Arrays;


/**
 * Implementation of a {@link Vector} where the indices of non-zero values are all >= 0; these values correspond to
 * a specified range within a {@link FloatBuffer}.  This class can be substantially more efficient than {@link DenseFloatArrayVector}
 * when the number and length of your vectors are known in advance, because allocating one large {@link FloatBuffer} to store
 * all the elements is cheaper than allocating many smaller arrays; you also have the opportunity to allocate the
 * {@link FloatBuffer} as a direct (off-heap) buffer.
 *
 * To attempt to allocate a direct, off-heap {@link FloatBuffer}, call:
 * <code>ByteBuffer.allocateDirect(4 * [# elements desired]).asFloatBuffer()</code>
 * (We say "attempt" because in principle not all platforms are guaranteed to support direct allocation.)
 *
 * If serialized, only the portion of the buffer containing vector data is saved, and these values are not "shared"
 * across multiple serialized {@link DenseFloatBufferVector}s.  Consequently, if you are serializing
 * a large number of "overlapping" vectors, this may require many more serialized bytes than the original memory
 * footprint.  Additionally, when deserialized, the memory footprint may likewise be much larger than the original.
 *
 * Note that the {@link DenseFloatBufferVector} is tied to the {@link FloatBuffer} storing its elements; changes to one affect the other.
 */
public final class DenseFloatBufferVector extends AbstractVector implements MutableDenseVector {
  private static final long serialVersionUID = 1;

  private transient int _offset; // not final so it can be set by readObject
  private final int _length;
  private transient FloatBuffer _buffer; // not final so it can be set by readObject

  /**
   * Creates a {@link DenseFloatBufferVector} backed by the provided {@link FloatBuffer}.  The vector takes as its initial
   * values whatever values there are in the corresponding portion of the FloatBuffer backing it, and subsequent changes
   * to one will affect the other.  Although offset may be > 0 to point to where the start of the vector's data is in
   * the buffer, the corresponding first element in the created vector always has index 0.
   *
   * @param buffer the underlying {@link FloatBuffer} that backs this vector
   * @param offset the offset within the FloatBuffer where this vector begins
   * @param length the length of this vector
   */
  public DenseFloatBufferVector(FloatBuffer buffer, int offset, int length) {
    if (buffer == null) {
      throw new NullPointerException("buffer must not be null");
    }
    if (offset < 0) {
      throw new IllegalArgumentException("offset must be >= 0");
    }
    if (length < 0) {
      throw new IllegalArgumentException("length must be >= 0");
    }
    if (offset + length > buffer.capacity()) {
      throw new IllegalArgumentException("the offset + length must not exceed the buffer's capacity");
    }

    _buffer = buffer;
    _offset = offset;
    _length = length;
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject(); // writes the length
    for (int i = _offset; i < _offset + _length; i++) {
      out.writeFloat(_buffer.get(i));
    }
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject(); // reads the length
    _offset = 0;
    float[] data = new float[_length];
    for (int i = 0; i < _length; i++) {
      data[i] = in.readFloat();
    }
    _buffer = FloatBuffer.wrap(data);
  }

  @Override
  public Class<? extends Number> valueType() {
    return float.class;
  }

  @Override
  public double get(long index) {
    if (index >= _length || index < 0) {
      return 0;
    } else {
      return getInternal((int) index);
    }
  }

  @Override
  public void copyTo(float[] dest, int start, int length) {
    _buffer.position(_offset);
    _buffer.get(dest, start, Math.min(_length, length));
    if (length > _length) {
      // zero out remaining array elements
      Arrays.fill(dest, start + _length, start + length, 0);
    }
  }

  @Override
  public VectorElementIterator iterator() {
    return new Iterator();
  }

  @Override
  public VectorElementIterator reverseIterator() {
    return new ReverseIterator();
  }

  @Override
  public long capacity() {
    return _length;
  }

  @Override
  public long maxCapacity() {
    return capacity();
  }

  @Override
  public void put(long index, double value) {
    if (index >= _length) {
      throw new IllegalArgumentException("Index is beyond the mutable length of the vector");
    }

    putInternal((int) index, (float) value);
  }

  private void putInternal(int index, float value) {
    _buffer.put(_offset + index, value);
  }

  private float getInternal(int index) {
    return _buffer.get(_offset + index);
  }

  @Override
  public void transformInPlace(VectorElementTransformer transformer) {
    for (int i = 0; i < _length; i++) {
      float value = getInternal(i);
      if (value != 0) {
        putInternal(i, (float) transformer.transform(i, value));
      }
    }
  }

  @Override
  public void addInPlace(Vector other) {
    if (other instanceof DenseFloatBufferVector) { // an opportunity to optimize aplenty
      addInPlace((DenseFloatBufferVector) other);
    } else { // do the standard thing
      MutableDenseVector.super.addInPlace(other);
    }
  }

  public void addInPlace(DenseFloatBufferVector vec) {
    for (int i = 0; i < _length; i++) {
      putInternal(i, getInternal(i) + vec.getInternal(i));
    }
    for (int i = _length; i < vec._length; i++) {
      if (vec.getInternal(i) != 0.0f) {
        throw new IndexOutOfBoundsException(
            "Attempted to modify DenseFloatBufferVector vector element beyond its length");
      }
    }
  }

  private class Iterator implements VectorElementIterator {
    private int _index = 0;

    @Override
    public void forEachRemaining(VectorElementConsumer consumer) {
      for (; _index < _length; _index++) {
        float val = getInternal(_index);
        if (val != 0) {
          consumer.consume(_index, val);
        }
      }
    }

    @Override
    public <T> T mapNext(VectorElementFunction<T> mapper) {
      float val;
      while ((val = getInternal(_index)) == 0) {
        _index++;
      }
      return mapper.apply(_index++, val);
    }

    @Override
    public void next(VectorElementConsumer consumer) {
      float val;
      while ((val = getInternal(_index)) == 0) {
        _index++;
      }
      consumer.consume(_index++, val);
    }

    @Override
    public boolean hasNext() {
      for (; _index < _length; _index++) {
        if (getInternal(_index) != 0) {
          return true;
        }
      }

      return false;
    }
  }

  private class ReverseIterator implements VectorElementIterator {
    private int _index = _length - 1;

    @Override
    public void forEachRemaining(VectorElementConsumer consumer) {
      for (; _index >= 0; _index--) {
        float val = getInternal(_index);
        if (val != 0) {
          consumer.consume(_index, val);
        }
      }
    }

    @Override
    public <T> T mapNext(VectorElementFunction<T> mapper) {
      float val;
      while ((val = getInternal(_index)) == 0) {
        _index--;
      }
      return mapper.apply(_index--, val);
    }

    @Override
    public void next(VectorElementConsumer consumer) {
      float val;
      while ((val = getInternal(_index)) == 0) {
        _index--;
      }
      consumer.consume(_index--, val);
    }

    @Override
    public boolean hasNext() {
      for (; _index >= 0; _index--) {
        if (getInternal(_index) != 0) {
          return true;
        }
      }

      return false;
    }
  }
}