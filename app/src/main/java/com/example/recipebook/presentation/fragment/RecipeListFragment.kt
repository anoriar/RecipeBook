package com.example.recipebook.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.R
import com.example.recipebook.databinding.FragmentRecipeListBinding
import com.example.recipebook.di.DaggerAppComponent
import com.example.recipebook.di.modules.AppModule
import com.example.recipebook.domain.entity.Recipe
import com.example.recipebook.presentation.adapter.CategoryFilterAdapter
import com.example.recipebook.presentation.adapter.RecipeListAdapter
import com.example.recipebook.presentation.viewmodel.RecipeListViewModel
import com.google.android.flexbox.*
import javax.inject.Inject


class RecipeListFragment : Fragment() {

    @Inject
    lateinit var recipeListViewModel: RecipeListViewModel
    private lateinit var recipeListAdapter: RecipeListAdapter
    private lateinit var categoryFilterAdapter: CategoryFilterAdapter

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
        initRecipeListRecyclerView()
        initCategoryFilterRecyclerView()
        initSearchView()
        initAddButton()
        initRecipeOnClickListener()
        initOnFavoriteClickListener()
        observeViewModel()
    }

    private fun initRecipeListRecyclerView(){
        val recyclerView: RecyclerView = binding.rvRecipesList
        recipeListAdapter = RecipeListAdapter()
        recyclerView.adapter = recipeListAdapter
    }

    private fun initCategoryFilterRecyclerView(){
        val recyclerView: RecyclerView = binding.rvCategoriesFilter
        categoryFilterAdapter = CategoryFilterAdapter()
        recyclerView.adapter = categoryFilterAdapter
        categoryFilterAdapter.onCategoryFilterClickListener = {
            if(!it.isSelected){
                recipeListViewModel.selectCategory(it.id)
            } else{
                recipeListViewModel.unselectCategory(it.id)
            }
        }

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerView.layoutManager = layoutManager

        recyclerView.itemAnimator = null
    }

    private fun initSearchView(){
        binding.svRecipes.setOnQueryTextListener(object:
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isEmpty()){
                    this.onQueryTextSubmit("");
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                recipeListViewModel.changeSearchQuery(query)
                return true
            }
        })
    }

    private fun initAddButton(){
        binding.buttonAddRecipe.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.recipe_book_container, RecipeAddEditFragment.getAddInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun observeViewModel(){
        recipeListViewModel.recipeListLiveData.observe(viewLifecycleOwner) {
            recipeListAdapter.submitList(it)
            Log.d("RECIPES", it.toString())
        }
        recipeListViewModel.categoriesFilter.observe(viewLifecycleOwner) {
            categoryFilterAdapter.submitList(it)
            Log.d("RECIPES", it.toString())
        }
    }


    private fun initRecipeOnClickListener() {
        recipeListAdapter.onRecipeClickListener = {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.recipe_book_container, RecipeDetailFragment.getInstance(it.id?:0))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun initOnFavoriteClickListener() {
        recipeListAdapter.onFavoriteClickListener = { recipe: Recipe, checked: Boolean ->
            when(checked){
                true -> recipeListViewModel.addRecipeToFavourites(recipe)
                false -> recipeListViewModel.deleteRecipeFromFavourites(recipe)
            }
        }
    }


    companion object {
        fun getInstance(): RecipeListFragment {
            return RecipeListFragment()
        }
    }
}