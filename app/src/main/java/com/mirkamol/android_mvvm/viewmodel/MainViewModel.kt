package com.mirkamol.android_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mirkamol.android_mvvm.model.Post
import com.mirkamol.android_mvvm.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    val allposts = MutableLiveData<ArrayList<Post>>()
    val deletedPost = MutableLiveData<Post>()

     fun apiPostList(): LiveData<ArrayList<Post>> {
        RetrofitHttp.postService.listPost().enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>
            ) {
                allposts.value = response.body()
                Log.d("TAG", "onResponse:${response.body()} ")
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                allposts.value = null
            }
        })
        return allposts
    }

     fun apiPostDelete(post: Post):LiveData<Post> {
        RetrofitHttp.postService.deletePost(post.id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
              deletedPost.value = response.body()

            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                deletedPost.value = null
                Log.d("TAG", "onResponse:${t.localizedMessage} ")
            }
        })

        return deletedPost
    }

}