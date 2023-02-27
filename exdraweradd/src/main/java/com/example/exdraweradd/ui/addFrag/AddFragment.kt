package com.example.exdraweradd.ui.addFrag

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exdraweradd.MainActivity
import com.example.exdraweradd.R
import com.example.exdraweradd.databinding.ActivityMainBinding
import com.example.exdraweradd.databinding.AlertdialogEdittextBinding
import com.example.exdraweradd.databinding.AppBarMainBinding
import com.example.exdraweradd.databinding.FragmentAddBinding
import com.google.android.material.navigation.NavigationView

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private lateinit var addAdapter: AddAdapter
    private lateinit var addViewModel: AddViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initRecycler()
        addNavigationItems()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dynamic_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_add -> {
                // handle add menu item click
                val builder = AlertDialog.Builder(requireContext())
                val builderItem = AlertdialogEdittextBinding.inflate(layoutInflater)
                val editText = builderItem.editText

                with(builder) {
                    setTitle("Input City")
                    setMessage("도시를 입력하시오")
                    setView(builderItem.root)
                    setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->
                        if (editText.text != null && (editText.text.toString() !in addAdapter.cityNames)) {
                            val cityData = CityData(
                                name = editText.text.toString(),
                                date = "2023년 2월 25일",
                                icon = R.drawable.va_sun,
                                temp = 3.0
                            )
                            addViewModel.addCityData(cityData)
                            Toast.makeText(
                                requireContext(),
                                "입력된 이름 : ${editText.text}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    show()
                }
                true
            }
            R.id.menu_delete -> {
                val sorted = addAdapter.checkedItems.sorted().reversed()
                Log.d("AddFragment", "$sorted")
                Log.d("AddFragment", "${addViewModel.datas.value}")
                for (i in sorted) {
                    addAdapter.cityNames.removeAt(i)
                    addViewModel.removeCityData(addViewModel.datas.value?.get(i)!!)
                    Log.d("AddFragment", "delete")
                }
                addAdapter.checkedItems.clear()
                Log.d("AddFragment", "${addViewModel.datas.value}")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    private fun initRecycler(){
        addViewModel = ViewModelProvider(requireActivity())[AddViewModel::class.java]
        addAdapter = AddAdapter(requireContext())
        binding.recyclerView.adapter = addAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(VerticalItemDecorator(20))
        binding.recyclerView.addItemDecoration(HorizontalItemDecorator(10))

        addViewModel.datas.observe(viewLifecycleOwner){
            addAdapter.datas = it
            addAdapter.notifyDataSetChanged()
        }
    }

    private fun addNavigationItems() {
        val activity = requireActivity() as MainActivity
        val navView = activity.findViewById<NavigationView>(R.id.nav_view)
        val menu = navView.menu

        addViewModel.datas.observe(viewLifecycleOwner) { cities ->
            // Remove all existing menu items
            menu.removeGroup(R.id.add_fragment)
            // Add new menu items for cities that are not in the menu
            cities.forEachIndexed { index, cityData ->
                if (menu.findItem(index) == null) {
                    menu.add(R.id.add_fragment, index, index, cityData.name).setIcon(cityData.icon)
                }
            }
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            val cityData = addViewModel.datas.value?.get(menuItem.itemId)
            if (cityData != null) {
                Toast.makeText(requireContext(), "${cityData.name}을 선택했습니다.", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}