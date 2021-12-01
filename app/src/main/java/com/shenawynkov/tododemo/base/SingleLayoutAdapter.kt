package com.shenawynkov.tododemo.base




abstract class SingleLayoutAdapter( genericClickListener:(item:Any)->Unit ,private val layoutId: Int) : MyBaseAdapter(genericClickListener) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }
}