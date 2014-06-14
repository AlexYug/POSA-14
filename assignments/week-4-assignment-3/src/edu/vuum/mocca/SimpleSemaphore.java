package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject (which is accessed via a Condition). It must
 *        implement both "Fair" and "NonFair" semaphore semantics,
 *        just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
	private Lock mLock = null;

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO - you fill in here
	private Condition mCondition = null;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	private volatile int mPermissions = 0;

    /**
     * Constructor initialize the data members.  
     */
    public SimpleSemaphore (int permits,
                            boolean fair)
    { 
        // TODO - you fill in here
    	mLock = new ReentrantLock(fair);
    	mCondition = mLock.newCondition();
    	mPermissions = permits;
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here
		mLock.lockInterruptibly();
    	try
    	{
    		while(mPermissions <= 0)
    		{
    			mCondition.await();
    		}
    		--mPermissions;
    	}
    	finally
    	{
    		mLock.unlock();
    	}
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here
		mLock.lock();
       	try
    	{
    		while(mPermissions <= 0)
    		{
    			mCondition.awaitUninterruptibly();
    		}
    		--mPermissions;
    	}
    	finally
    	{
    		mLock.unlock();
    	}
    }

    /**
     * Return one permit to the semaphore.
     */
    public void release() {
        // TODO - you fill in here
   		mLock.lock();
       	try
    	{
    		++mPermissions;
    		if(mPermissions > 0)
    		{
    			mCondition.signal();
    		}
    	}
    	finally
    	{
    		mLock.unlock();
    	}
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits(){
    	// TODO - you fill in here
    	return mPermissions; //< No lock is needed since mPermissions is volatile.
    }
}
