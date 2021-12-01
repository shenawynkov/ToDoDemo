package com.shenawynkov.tododemo.base



open class GenericAdapter(genericClickListener:(item:Any)->Unit,var list: ArrayList<*>, layout:Int) :
    SingleLayoutAdapter(genericClickListener,layout){
    override fun getObjForPosition(position: Int): Any {
        return list.get(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}