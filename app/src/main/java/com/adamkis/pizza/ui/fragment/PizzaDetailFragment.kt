package com.adamkis.pizza.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adamkis.pizza.R
import com.adamkis.pizza.helper.FilePersistenceHelper
import com.adamkis.pizza.model.Pizza
import kotlinx.android.synthetic.main.fragment_pizza_detail.*


/**
 * Created by adam on 2018. 01. 11..
 */
class PizzaDetailFragment : Fragment() {

    private var pizza: Pizza? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            pizza = arguments!!.getParcelable(ARG_PIZZA)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pizza_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bitmap: Bitmap? = FilePersistenceHelper.loadBitmapFromFile(activity as Context)
        header_image.setImageBitmap(bitmap)
//        fillInfo(view, pizza)
    }

//    private fun fillInfo(view: View, photo: Photo?){
//        if (photo == null) return
//        recents_photo_id.text = photo?.id
//        val row1 = view.findViewById<View>(R.id.info_row_1)
//        val row2 = view.findViewById<View>(R.id.info_row_2)
//        val row3 = view.findViewById<View>(R.id.info_row_3)
//        val row4 = view.findViewById<View>(R.id.info_row_4)
//        val row5 = view.findViewById<View>(R.id.info_row_5)
//        val row6 = view.findViewById<View>(R.id.info_row_6)
//        row1.findViewById<TextView>(R.id.info_title).text = photo!!::farm.name
//        row1.findViewById<TextView>(R.id.info_value).text = photo?.farm
//        row2.findViewById<TextView>(R.id.info_title).text = photo!!::owner.name
//        row2.findViewById<TextView>(R.id.info_value).text = photo?.owner
//        row3.findViewById<TextView>(R.id.info_title).text = photo!!::server.name
//        row3.findViewById<TextView>(R.id.info_value).text = photo?.server
//        row4.findViewById<TextView>(R.id.info_title).text = photo!!::isfriend.name
//        row4.findViewById<TextView>(R.id.info_value).text = photo?.isfriend.toString()
//        row5.findViewById<TextView>(R.id.info_title).text = photo!!::isfamily.name
//        row5.findViewById<TextView>(R.id.info_value).text = photo?.isfamily.toString()
//        row6.findViewById<TextView>(R.id.info_title).text = photo!!::ispublic.name
//        row6.findViewById<TextView>(R.id.info_value).text = photo?.ispublic.toString()
//    }

    companion object {

        // TODO use Pizza.TAG
        private val ARG_PIZZA = "ARG_PIZZA"

        fun newInstance(pizza: Pizza): PizzaDetailFragment {
            val fragment = PizzaDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_PIZZA, pizza)
            fragment.arguments = args
            return fragment
        }

    }

}