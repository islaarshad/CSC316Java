package edu.ncsu.csc316.security_log.dictionary;



/**
 * The hash table implementation
 * @author Islahuddin Arshad
 *
 * @param <E> generic elements are inferred
 */
public class HashTable<E> {

    /** Hash table*/
    private E[] hashTable;
    /**size of hashtable array*/
    private int size;
    /** number of items in the table*/
    private int itemsInTable;
    /**The load factor*/
    private double factor = 0.83;
//    /**The lookup counter*/
//    private int lookUpCounter;
//    /**The checking probles*/
//    private int checkingProbes;


    /**
     * Constructs a new generic HashTable with
     * some initial default capacity
     */
    @SuppressWarnings("unchecked")
    public HashTable()
    {
    	this.size = 300;
        hashTable = (E[]) new Object[this.size] ;
        itemsInTable = 0;



    }
    
    /**
     * Resizes the hashtable
     */
    @SuppressWarnings("unchecked")
	public void resize() {
        this.size *= 2;
        this.itemsInTable = 0;
        E [] values = this.hashTable.clone();
        this.hashTable = (E[]) new Object[this.size];
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                insert(values[i]);
            }
        }
    }



    /**
     * This will compressed the generated hashcode through the GOLDEN RATIO
     * @param hashCode to be compressed
     * @return the compressed hashCode
     */
    public int compress(int hashCode) {
    	double gRatio =  ((Math.sqrt(5) - 1 ) / 2);
        double p = this.size;
        int result = (int) Math.floor(p * ((hashCode * gRatio) - Math.floor(hashCode * gRatio)));
        return result % this.size;
    }


    /**
     * Inserts the generic value E into the hash table
     * @param value - the value to insert into the hash table
     */
    public void insert(E value) {
    	int hash = value.hashCode();

        int compressed = this.compress(hash);

        if (hashTable[compressed] != null) {
                compressed = this.resolveCollisions(this.getTable(), compressed, value);
                hashTable[compressed] = value;
                itemsInTable++;
        } else {

            hashTable[compressed] = value;
            itemsInTable++;
        }

        if (getHashTableLength() > factor)
        {
            resize();
        }
    }

    /**
     * This will collisions in the hashtable when inserting
     * @param hashT the hash table created
     * @param hashCode generated by the value in the table
     * @param value the generic value
     * @return int the hash code of the value
     */
    public int resolveCollisions(E[] hashT, int hashCode, E value) {
    	if (hashCode < 0) {
            hashCode *= -1;
        } else if (hashCode == 0) {
            hashCode += 1;
        }
        
        while (hashT[hashCode] != null) {
          
            hashCode++;
            hashCode = hashCode % hashT.length;
        }

        return hashCode;
    }


    /**
     * Returns the hash table
     * @return E[] generic entries
     */
    public E[] getTable() {
        return hashTable;
    }

    /**
     * Finds the value E in
     * the hash table. Returns the value E
     * if the value was found in the hash table.
     * If the value is not in the hash table, return null.
     *
     * @param value - the value to search for in the hash table
     * @return the reference to the value in the hash table, or null if the value
     *              is not in the hash table
     */
    public E lookUp(E value) {
    	//lookUpCounter++;
        int hash = compress(value.hashCode());
        if(this.getTable()[hash] == null) {
            return null;
        }

        if(this.getTable()[hash].equals(value)){
            //checkingProbes++;
            return value;
        } else {

            E compare = value;
            while(this.getTable()[hash] != null ) {
               
                if(this.getTable()[hash].equals(compare)) {
                    //checkingProbes++;
                    return value;
                } else {
                    //checkingProbes++;
                    hash = (hash + 1) % this.size;
                }
            }
            return null;
        }
      
    }


    /**
     * For double hashing, hashes the first value
     * @param value the value to be hashed
     * @return int the hash code of the first value
     */
    public int hasher1(E value) {
        return value.hashCode();
    }

    /**
     * For double hashing, hashes the second value
     * @param value the value to be hashed
     * @return int the hash code of the second value
     */
    public int hasher2(E value) {
        return 3 - value.hashCode() % this.size;
    }


    /**
     * Returns the number of values in the hash table
     *
     * @return the number of values in the hash table
     */
    public int size()
    {
        return itemsInTable;
    }

    /**
     * Returns the length/capacity of the hash table
     *
     * @return the length/capacity of the hash table
     */
    public int getHashTableLength()
    {
        return (itemsInTable / this.size);
    }
}