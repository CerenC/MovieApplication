package com.cs.ceren.moviedemo.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 *
 *
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 *
 *
 * Note that only one observer is going to be notified of changes.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)
    private val mObservers = mutableSetOf<Observer<T>>()

    private val mInternalObserver = Observer<T> { t ->
        if (mPending.compareAndSet(true, false)) {
            mObservers.forEach { observer ->
                observer.onChanged(t)
            }
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        mObservers.add(observer)

        if (!hasObservers()) {
            super.observe(owner, mInternalObserver)
        }
    }

    override fun removeObservers(owner: LifecycleOwner) {
        mObservers.clear()
        super.removeObservers(owner)
    }

    override fun removeObserver(observer: Observer<T>) {
        mObservers.remove(observer)
        super.removeObserver(observer)
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {

        private val TAG = "SingleLiveEvent"
    }
}