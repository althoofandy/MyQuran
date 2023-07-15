package com.example.myquran.ui.surah.detailsurah

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquran.R
import com.example.myquran.databinding.ActivityDetailSurahBinding
import com.example.myquran.model.SurahResponse

class DetailSurahActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSurahBinding
    private lateinit var viewModel: DetailSurahViewModel
    private lateinit var adapter: AyatAdapter
    private lateinit var mediaPlayer: MediaPlayer

    companion object{
        const val DATA_SURAH = "data_surah"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailSurahViewModel::class.java]

        val dataSurah = intent.getParcelableExtra<SurahResponse>(DATA_SURAH)

        binding.rvAyatSurah.layoutManager = LinearLayoutManager(this)
        binding.rvAyatSurah.setHasFixedSize(true)

        binding.apply {
            val spannedText = Html.fromHtml(dataSurah?.keterangan, Html.FROM_HTML_MODE_COMPACT)
            tvJudulSurah.text = dataSurah?.nama
            tvJudulSurahArabic.text = dataSurah?.asma
            tvArtiSurah.text = dataSurah?.arti
            tvAyatSurah.text = "${ dataSurah?.ayat.toString() } Ayat"
            tvDescSurah.text = spannedText
            viewModel.getListAyat(dataSurah?.nomor.toString())
            viewModel.getDataAyat().observe(this@DetailSurahActivity){
                adapter = AyatAdapter(this@DetailSurahActivity,it)
                rvAyatSurah.adapter = adapter
            }

            val audio = convertHttpToHttps(dataSurah?.audio!!)
            fabAudio.setOnClickListener {
                if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    fabAudio.setImageResource(R.drawable.baseline_play_arrow)
                } else {
                    mediaPlayer = MediaPlayer()
                    mediaPlayer.setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    mediaPlayer.setDataSource(audio)
                    mediaPlayer.prepareAsync()
                    mediaPlayer.setOnPreparedListener { mp ->
                        mp.start()
                        fabAudio.setImageResource(R.drawable.baseline_pause)
                    }
                    mediaPlayer.setOnCompletionListener {
                        fabAudio.setImageResource(R.drawable.baseline_play_arrow)
                    }
                }
            }
        }
        }
    fun convertHttpToHttps(url: String): String {
        return url.replace("http://", "https://")
    }
    }
