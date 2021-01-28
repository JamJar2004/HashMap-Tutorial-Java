import java.util.*;

public class HashMap<K, V>
{
    private Entry<K, V>[] m_buckets;
    private int           m_count;

    private final float m_loadFactor;
    private       int   m_maxCount;

    public HashMap() { this(16, 0.75f); }

    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity, float loadFactor)
    {
        m_buckets    = new Entry[initialCapacity];
        m_count      = 0;
        m_loadFactor = loadFactor;
        m_maxCount   = (int)(loadFactor * initialCapacity);
    }

    private boolean PlaceInBucket(int bucketIndex, K key, V value)
    {
        Entry<K, V> lastEntry = null;
        Entry<K, V> currEntry = m_buckets[bucketIndex];

        int keyHash = key == null ? 0 : key.hashCode();

        while(currEntry != null)
        {
            if(currEntry.GetHashCode() == keyHash)
            {
                if(currEntry.GetKey().equals(key))
                {
                    currEntry.SetValue(value);
                    return true;
                }
            }
            lastEntry = currEntry;
            currEntry = currEntry.GetNext();
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        if(lastEntry == null)
            m_buckets[bucketIndex] = newEntry;
        else
            lastEntry.SetNext(newEntry);

        m_count++;

        if(m_count > m_maxCount)
            Reallocate();

        return false;
    }

    private Entry<K, V> GetFromBucket(int bucketIndex, K key)
    {
        Entry<K, V> currEntry = m_buckets[bucketIndex];

        int keyHash = key == null ? 0 : key.hashCode();

        while(currEntry != null)
        {
            if(currEntry.GetHashCode() == keyHash)
            {
                if(currEntry.GetKey().equals(key))
                    return currEntry;
            }
            currEntry = currEntry.GetNext();
        }

        return null;
    }

    private boolean RemoveFromBucket(int bucketIndex, K key)
    {
        Entry<K, V> lastEntry = null;
        Entry<K, V> currEntry = m_buckets[bucketIndex];

        int keyHash = key == null ? 0 : key.hashCode();

        while(currEntry != null)
        {
            if(currEntry.GetHashCode() == keyHash)
            {
                if(currEntry.GetKey().equals(key))
                {
                    if(lastEntry == null)
                        m_buckets[bucketIndex] = currEntry.GetNext();
                    else
                        lastEntry.SetNext(currEntry.GetNext());

                    m_count--;
                    return true;
                }
            }
            lastEntry = currEntry;
            currEntry = currEntry.GetNext();
        }

        return false;
    }

    private int GetBucketIndex(K key)
    {
        int keyHash = key == null ? 0 : key.hashCode();
        return Math.abs(keyHash % m_buckets.length);
    }

    @SuppressWarnings("unchecked")
    private void Reallocate()
    {
        Entry<K, V>[] newBuckets = new Entry[m_buckets.length * 2];
        Entry<K, V>[] oldBuckets = m_buckets;

        m_buckets = newBuckets;
        m_maxCount = (int)(m_loadFactor * m_buckets.length);
        m_count = 0;
        for(Entry<K, V> firstEntry : oldBuckets)
        {
            Entry<K, V> currEntry = firstEntry;
            while(currEntry != null)
            {
                PlaceInBucket(GetBucketIndex(currEntry.GetKey()), currEntry.GetKey(), currEntry.GetValue());
                currEntry = currEntry.GetNext();
            }
        }
    }

    public int Count() { return m_count; }

    public V Get(K key)
    {
        Entry<K, V> foundEntry = GetFromBucket(GetBucketIndex(key), key);
        if(foundEntry == null)
            return null;

        return foundEntry.GetValue();
    }

    public boolean Place(K key, V value)
    {
        return PlaceInBucket(GetBucketIndex(key), key, value);
    }

    public boolean Remove(K key)
    {
        return RemoveFromBucket(GetBucketIndex(key), key);
    }

    public void Clear()
    {
        for(int i = 0; i < m_buckets.length; i++)
        {
            Entry<K, V> currEntry = m_buckets[i];
            while(currEntry != null)
            {
                currEntry = currEntry.GetNext();
            }
            m_buckets[i] = null;
        }
        m_count = 0;
    }

    public KeyCollection   GetKeys()   { return new KeyCollection();   }
    public ValueCollection GetValues() { return new ValueCollection(); }

    public EntryCollection GetEntries() { return new EntryCollection(); }

    public static class Entry<K, V>
    {
        private final int m_hashCode;

        private final K m_key;
        private       V m_value;

        private Entry<K, V> m_next;

        public Entry(K key, V value)
        {
            m_hashCode = key == null ? 0 : key.hashCode();

            m_key   = key;
            m_value = value;
            m_next  = null;
        }

        public int GetHashCode() { return m_hashCode; }

        public K GetKey()   { return m_key;   }
        public V GetValue() { return m_value; }

        public Entry<K, V> GetNext() { return m_next; }

        public void SetValue(V value) { m_value = value; }

        public void SetNext(Entry<K, V> next) { m_next = next; }
    }

    public abstract class HashIterator<T> implements Iterator<T>
    {
        private int         m_bucketIndex;
        private Entry<K, V> m_currEntry;

        public HashIterator()
        {
            m_bucketIndex = 0;
            m_currEntry   = m_buckets[m_bucketIndex];
            while(m_currEntry == null)
            {
                m_bucketIndex++;
                if(m_bucketIndex >= m_buckets.length)
                    break;

                m_currEntry = m_buckets[m_bucketIndex];
            }
        }

        public Entry<K, V> NextEntry()
        {
            Entry<K, V> result = m_currEntry;
            m_currEntry = m_currEntry.GetNext();
            while(m_currEntry == null)
            {
                m_bucketIndex++;
                if(m_bucketIndex >= m_buckets.length)
                    break;

                m_currEntry = m_buckets[m_bucketIndex];
            }
            return result;
        }

        @Override
        public boolean hasNext() { return m_currEntry != null; }
    }

    public class KeyIterator extends HashIterator<K>
    {
        @Override
        public K next() { return NextEntry().GetKey(); }
    }

    public class ValueIterator extends HashIterator<V>
    {
        @Override
        public V next() { return NextEntry().GetValue(); }
    }

    public class EntryIterator extends HashIterator<Entry<K, V>>
    {
        @Override
        public Entry<K, V> next() { return NextEntry(); }
    }

    public class KeyCollection implements Iterable<K>
    {
        @Override
        public Iterator<K> iterator() { return new KeyIterator(); }
    }

    public class ValueCollection implements Iterable<V>
    {
        @Override
        public Iterator<V> iterator() { return new ValueIterator(); }
    }

    public class EntryCollection implements Iterable<Entry<K, V>>
    {
        @Override
        public Iterator<Entry<K, V>> iterator() { return new EntryIterator(); }
    }
}
