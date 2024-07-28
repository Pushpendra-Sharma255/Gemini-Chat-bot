package ai.google.gemini.Activity

import ai.google.gemini.Adapter.ChatAdapter
import ai.google.gemini.Api.ApiInterface
import ai.google.gemini.Api.ApiRepository
import ai.google.gemini.Api.RetrofitHelper
import ai.google.gemini.Models.ChatModel
import ai.google.gemini.Models.ContentX
import ai.google.gemini.Models.PartX
import ai.google.gemini.Models.RawInput
import ai.google.gemini.R
import ai.google.gemini.ViewModel.MainViewModel
import ai.google.gemini.ViewModel.ViewModelFactory
import ai.google.gemini.databinding.ActivityMainBinding

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ChatAdapter
    private val chatList: ArrayList<ChatModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiRepository(
                    RetrofitHelper.getInstance().create(ApiInterface::class.java)
                )
            )
        )[MainViewModel::class.java]

        viewModel.date.observe(this) {
            val chatModel = ChatModel(it.candidates[0].content.parts[0].text, true)
            val size = chatList.size
            chatList[size - 1] = chatModel
            val gson = Gson()
            val str = gson.toJson(chatList)
            adapter.notifyDataSetChanged()
            scrollToBottom()
        }
        binding.ivSend.setOnClickListener {
            val msg = binding.etMsg.text.toString();
            if (msg.isNotEmpty()) {
                binding.etMsg.setText("")
                getResult(msg)
            }
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        setUpChatRv()


        binding.rootView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            private var isKeyboardOpen = false

            override fun onGlobalLayout() {
                val rect = Rect()
                binding.rootView.getWindowVisibleDisplayFrame(rect)
                val screenHeight = binding.rootView.rootView.height
                val keypadHeight = screenHeight - rect.bottom

                // Check if the keyboard is open (assuming 200 pixels is the threshold)
                val isOpen = keypadHeight > screenHeight * 0.15

                if (isOpen != isKeyboardOpen) {
                    isKeyboardOpen = isOpen

                    if (isKeyboardOpen) {
                        scrollToBottom()
                    }
                } else {

                }
            }

        })


    }

    private fun setUpChatRv() {
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(chatList)
        binding.rvChat.adapter = adapter
    }

    private fun getResult(msg: String) {
        chatList.add(ChatModel(msg, false))
        adapter.notifyDataSetChanged()

        Handler().postDelayed({
            chatList.add(ChatModel(" Typing ", true))
            adapter.notifyDataSetChanged()
            binding.rvChat.smoothScrollToPosition(chatList.size)

        }, 500)

        val partX =
            PartX(text = msg)
        val contentX = ContentX(parts = listOf(partX))
        val manualRawInput = RawInput(contents = listOf(contentX))
        viewModel.getResult(manualRawInput)
    }

    private fun scrollToBottom() {
        Handler().postDelayed({
            binding.rvChat.smoothScrollToPosition(chatList.size)
        }, 500)
    }
}