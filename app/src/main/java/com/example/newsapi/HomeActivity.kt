package com.example.newsapi

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.example.newsapi.adapter.NewsAdapter
import com.example.newsapi.api.ApiManager
import com.example.newsapi.api.model.NewsResponse
import com.example.newsapi.api.model.Source
import com.example.newsapi.api.model.SourceResponse
import com.example.newsapi.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : BaseActivity() {
    val newsAdapter = NewsAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recycler_view.adapter=newsAdapter
        getSource();
    }
    fun getSource(){
        ApiManager.getApis()
            .getSources()
            .enqueue(object:Callback<SourceResponse>{
                override fun onResponse(
                    call: Call<SourceResponse>,
                    response: Response<SourceResponse>
                ) {
                   if(response.isSuccessful&&response.body()?.status.equals("ok")){
                       showTabs(response.body()?.sources)
                   }else{
                       showMessage(message = response.body()?.message?:"",
                           posActionName = getString(R.string.ok),
                           posAction = DialogInterface.OnClickListener { dialog, which ->
                               dialog.dismiss()
                           })
                   }
                }

                override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                    showMessage(message = t.localizedMessage,
                    posActionName = getString(R.string.ok),
                        posAction = DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        }
                    )
                }
            })
    }
    fun getNews(sourcId:String?){
        ApiManager.getApis().getNews(sourcId!!)
            .enqueue(object :Callback<NewsResponse>{
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    showMessage(message = t.localizedMessage,
                        posActionName = getString(R.string.ok),
                        posAction = DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        }
                    )
                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progress_bar.visibility=View.GONE
                    if(response.isSuccessful&&response.body()?.status.equals("ok")){
                       val list = response.body()?.articles
                       newsAdapter.changeData(list)
                    }else{
                        showMessage(message = response.body()?.message?:"",
                            posActionName = getString(R.string.ok),
                            posAction = DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            })
                    }
                }
            }
            )
    }

    fun showTabs(sources:List<Source?>?){
        sources?.forEach {item->
            val tab = tab_Layout.newTab()
            tab.setText(item?.name)
            tab.setTag(item)
            tab_Layout.addTab(tab)
        }
        tab_Layout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    // call api to get news
                    val source = tab?.tag as Source
                    getNews(source?.id)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    //call api to get news
                    val source = tab?.tag as Source
                    getNews(source?.id)
                }
            }
        )
        tab_Layout.getTabAt(0)?.select()
    }
}