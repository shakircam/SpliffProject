package com.shakir.spliff.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakir.spliff.R
import com.shakir.spliff.adapter.ItemClickListener
import com.shakir.spliff.adapter.ProductAdapter
import com.shakir.spliff.data.model.ProductData
import com.shakir.spliff.data.viewmodel.ProductViewModel
import com.shakir.spliff.databinding.FragmentFlowersBinding
import com.shakir.spliff.ui.activity.DetailsActivity


class FlowersFragment : Fragment(),ItemClickListener, SearchView.OnQueryTextListener{
    private var _binding : FragmentFlowersBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { ProductAdapter(this) }
    private lateinit var myViewModel: ProductViewModel
    val items = mutableListOf<ProductData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFlowersBinding.inflate(inflater, container, false)
        val array = ArrayList<String>()
        array.add("https://images.pexels.com/photos/60597/dahlia-red-blossom-bloom-60597.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1")
        array.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/Flower_July_2011-2_1_cropped.jpg/1200px-Flower_July_2011-2_1_cropped.jpg")
        array.add("https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729512__340.jpg")
        array.add("https://thefinancialexpress.com.bd/uploads/1549955770.jpg")
        array.add("https://i.ytimg.com/vi/pZVdQLn_E5w/maxresdefault.jpg")
        array.add("https://thumbs.dreamstime.com/b/purple-flower-2212075.jpg")
        array.add("https://media.newyorker.com/photos/6095a6b1d9c1b4ec8a8eb8da/master/pass/Stevens-FlowersOKeeffe.jpg")
        array.add("https://merriam-webster.com/assets/mw/images/gallery/gal-wap-slideshow-slide/image1051227931-6473-fadbad17da4a8a8e03cf1a41237a834b@1x.jpg")
        array.add("https://hgtvhome.sndimg.com/content/dam/images/hgtv/fullset/2020/8/20/0/CI_Ball-Horticultural-Co_Sorbet-Black-Delight-viola.jpg.rend.hgtvcom.616.822.suffix/1597923373681.jpeg")
        array.add("https://hgtvhome.sndimg.com/content/dam/images/hgtv/stock/2018/3/2/shutterstock_anemone-134595248.jpg.rend.hgtvcom.616.411.suffix/1519931799331.jpeg")

        myViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        initRecyclerView()

            val productData1 = ProductData(1,"Rose",10,(array[0]),"This is a rose")
            val productData2 = ProductData(2,"Sunflower",20,(array[1]),"This is a Sun.")
            val productData3 = ProductData(3,"Lotus",15,(array[2]),"This is a Lotus")
            val productData4 = ProductData(4,"Daisy",13,(array[3]),"This is a Daisy")
            val productData5 = ProductData(5,"Lilly",8,(array[4]),"This is a Lilly")
            val productData6 = ProductData(6,"WaterLilly",18,(array[5])," Water lilly")
            val productData7 = ProductData(7,"New yorker",14,(array[6]),"New yorker")
            val productData8 = ProductData(8,"Nice",10,(array[7]),"Nice Flower")
            val productData9 = ProductData(9,"Black",20,(array[8]),"Black Flower")
            val productData10 = ProductData(10,"Flower",30,(array[9]),"Flower")

            val list = ArrayList<ProductData>()
            list.add(productData1)
            list.add(productData2)
            list.add(productData3)
            list.add(productData4)
            list.add(productData5)
            list.add(productData6)
            list.add(productData7)
            list.add(productData8)
            list.add(productData9)
            list.add(productData10)
            myViewModel.insertData(list)


        myViewModel.getAllData.observe(viewLifecycleOwner,{
            items.addAll(it)
            adapter.setData(it)
        })

        binding.searchView.setOnQueryTextListener(this)

        return binding.root
    }

    private fun initRecyclerView() {

        val mRecyclerView = binding.recyclerview
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
    }


    override fun onItemClick(position: Int) {
        val item = items[position]
        Toast.makeText(requireContext(),item.title,Toast.LENGTH_SHORT).show()

        val intent = Intent(activity, DetailsActivity::class.java)
        val title = item.title
        val description = item.description
        val price = item.price
        val image = item.image
        intent.putExtra("title",title)
        intent.putExtra("description",description)
        intent.putExtra("price",price)
        intent.putExtra("image",image)
        startActivity(intent)
    }

    override fun onAddClick() {
        /*val action = FlowersFragmentDirections.actionFlowersFragment2ToAddToFragment()
        view?.findNavController()?.navigate(action)*/
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"

        myViewModel.searchDatabase(searchQuery).observe(this,{list ->
            list?.let {
                adapter.setData(it)
            }
        })
    }


}