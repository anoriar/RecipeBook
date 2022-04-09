package com.example.recipebook.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.databinding.FragmentRecipeListBinding
import com.example.recipebook.di.DaggerAppComponent
import com.example.recipebook.di.modules.AppModule
import com.example.recipebook.presentation.adapter.RecipeListAdapter
import com.example.recipebook.presentation.viewmodel.RecipeListViewModel
import javax.inject.Inject


class RecipeListFragment : Fragment() {

    @Inject
    lateinit var recipeListViewModel: RecipeListViewModel
    private lateinit var recipeListAdapter: RecipeListAdapter

    private var _binding: FragmentRecipeListBinding? = null
    private val binding: FragmentRecipeListBinding
        get() {
            return _binding ?: throw RuntimeException("Binding can not be null")
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAppComponent.builder().appModule(AppModule(requireActivity().application)).build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView(){
        val recyclerView: RecyclerView = binding.rvRecipesList
        recipeListAdapter = RecipeListAdapter()
        recyclerView.adapter = recipeListAdapter
    }

    private fun observeViewModel(){
        recipeListViewModel.recipeListLiveData.observe(viewLifecycleOwner) {
            recipeListAdapter.submitList(it)
            Log.d("RECIPES", it.toString())
        }
    }

    companion object {
        fun getInstance(): RecipeListFragment {
            return RecipeListFragment()
        }
    }
}