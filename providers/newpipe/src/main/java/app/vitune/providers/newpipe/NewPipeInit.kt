package app.vitune.providers.newpipe

import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.exceptions.ExtractionException
import org.schabi.newpipe.extractor.stream.StreamInfo

object NewPipeInit {
    fun init() {
        NewPipe.init(DownloaderImpl.init(null))
    }

    fun getAudioStreams(mediaId: String): String {
        val infoNewPipe = StreamInfo.getInfo(
            ServiceList.YouTube,
            "https://youtube.com/watch?v=$mediaId"
        ) ?: throw ExtractionException("couldn't get info about audio with NewPipe")

        val audioStream = infoNewPipe.audioStreams.maxByOrNull { it.averageBitrate }
            ?: throw ExtractionException("couldn't get valid audio stream with NewPipe")

        return audioStream.content.toString()
    }
}
