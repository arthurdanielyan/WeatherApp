package com.bignerdranch.android.weather.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveData<T>(initialValue: T?) : MutableLiveData<T>(initialValue) {

    constructor() : this(null)

    private var isPending = false

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) {
            if(isPending) {
                observer.onChanged(it)
                isPending = false
            }
        }
    }

    override fun setValue(value: T) {
        isPending = true
        super.setValue(value)
    }

    override fun postValue(value: T) {
        isPending = true
        super.postValue(value)
    }
}