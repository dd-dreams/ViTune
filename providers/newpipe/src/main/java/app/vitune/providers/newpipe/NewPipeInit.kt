package app.vitune.providers.newpipe

import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.exceptions.ExtractionException
import org.schabi.newpipe.extractor.stream.StreamExtractor

object NewPipeInit {
    init {
        NewPipe.init(DownloaderImpl.init(null))
    }

    private fun getExtractor(url: String): StreamExtractor {
        return ServiceList.YouTube.getStreamExtractor(url)
            ?: throw ExtractionException("couldn't get info about audio with NewPipe")
    }

    fun getAudioStreams(mediaId: String): String {
        val streams = getExtractor("https://youtube.com/watch?v=$mediaId")

        val audioStream =  streams.audioStreams.maxByOrNull { it.bitrate }
            ?: throw ExtractionException("couldn't get valid audio stream with NewPipe")

        return audioStream.content.toString()
    }
}
