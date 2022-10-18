package com.umutcansahin.besinbesin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.umutcansahin.besinbesin.R
import com.umutcansahin.besinbesin.databinding.FragmentBesinDetayBinding
import com.umutcansahin.besinbesin.util.gorselIndir
import com.umutcansahin.besinbesin.util.placeHolderYap
import com.umutcansahin.besinbesin.viewmodel.BesinDetayiViewModel
import kotlinx.android.synthetic.main.fragment_besin_detay.*

class BesinDetayFragment : Fragment() {

    private lateinit var viewModel : BesinDetayiViewModel
    private var besinId = 0
    private lateinit var dataBinding : FragmentBesinDetayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_besin_detay,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinId = BesinDetayFragmentArgs.fromBundle(it).besinId
        }

        viewModel = ViewModelProviders.of(this).get(BesinDetayiViewModel :: class.java)
        viewModel.roomVerisiniAl(besinId)

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin->


            besin?.let {
                dataBinding.secilenBesin = it
            /*
                besinIsim.text = it.besinIsmi
                besinKalori.text = it.besinKalori
                besinKarbonhidrat.text = it.besinKarbonhidrat
                besinProtein.text = it.besinProtein
                besinYag.text = it.besinYag
                context?.let {
                    besinImage.gorselIndir(besin.besinGorsel, placeHolderYap(it))
                }
             */

            }
        })
    }
}