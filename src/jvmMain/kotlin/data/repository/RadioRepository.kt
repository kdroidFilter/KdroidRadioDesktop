package data.repository

import data.model.RadioItem
import data.model.RadioSubItem
import enums.RadioCategoryType
import utils.stringResource

object RadioRepository {
    fun getRadioItems(): List<RadioItem> {
        return listOf(
            RadioItem(
                stringResource("radio_kol_play"),
                "https://cdn.cybercdn.live/Kol_Barama/Music/icecast.audio",
                "assets/radiosIcons/kolplay.png"
            ),
            RadioItem(
                stringResource("radio_kol_barama"),
                "https://cdn.cybercdn.live/Kol_Barama/Live_Audio/icecast.audio",
                "assets/radiosIcons/kolbarama.jpg",
                RadioCategoryType.NEWS
            ),
            RadioItem(
                stringResource("radio_kol_hay"),
                "https://media2.93fm.co.il/live-new",
                "assets/radiosIcons/kolhay0.jpg",
                RadioCategoryType.NEWS
            ),
            RadioItem(
                icon ="assets/radiosIcons/kolhay.png",
                stations = listOf(
                    RadioSubItem(
                        stringResource("radio_category_kol_hayi_muzik"),
                        "https://live.kcm.fm/livemusic"
                    ),
                    RadioSubItem("נכנסים לקצב", "https://live.kcm.fm/02"),
                    RadioSubItem("הפלייליסט", "https://live.kcm.fm/35"),
                    RadioSubItem("ישי ריבו", "https://live.kcm.fm/39"),
                    RadioSubItem("בטעם של פעם", "https://live.kcm.fm/5"),
                    RadioSubItem("שעת כושר", "https://live.kcm.fm/47"),
                    RadioSubItem("הופעות", "https://live.kcm.fm/34"),
                    RadioSubItem("English Classics", "https://live.kcm.fm/22"),
                    RadioSubItem("ערוצי הקודש", "https://live.kcm.fm/38"),
                    RadioSubItem("קרליבך", "https://live.kcm.fm/7"),
                    RadioSubItem("אברהם פריד", "https://live.kcm.fm/15"),
                    RadioSubItem("מרדכי בן דוד", "https://live.kcm.fm/16"),
                    RadioSubItem("חיים בנט", "https://live.kcm.fm/23"),
                    RadioSubItem("חיים ישראל", "https://live.kcm.fm/26"),
                    RadioSubItem("משה לאופר", "https://live.kcm.fm/29"),
                    RadioSubItem("יעקב שוואקי", "https://live.kcm.fm/30"),
                    RadioSubItem("פרחי מיאמי", "https://live.kcm.fm/31"),
                    RadioSubItem("בעלזא", "https://live.kcm.fm/32"),
                    RadioSubItem("ליפא שמלצער", "https://live.kcm.fm/33"),
                    RadioSubItem("יהודי מקורי", "https://live.kcm.fm/3"),
                    RadioSubItem("ים תיכוני", "https://live.kcm.fm/4"),
                    RadioSubItem("רק ילדים", "https://live.kcm.fm/6"),
                    RadioSubItem("על הפארענצ׳עס", "https://live.kcm.fm/8"),
                    RadioSubItem("חב״ד", "https://live.kcm.fm/9"),
                    RadioSubItem("נעימות", "https://live.kcm.fm/10"),
                    RadioSubItem("בין הערביים", "https://live.kcm.fm/11"),
                    RadioSubItem("חזנות", "https://live.kcm.fm/12"),
                    RadioSubItem("פיוטים", "https://live.kcm.fm/13"),
                    RadioSubItem("ווקאלי", "https://live.kcm.fm/14"),
                    RadioSubItem("תענוג לשבת", "https://live.kcm.fm/17"),
                    RadioSubItem("עולים למירון", "https://live.kcm.fm/18"),
                    RadioSubItem("קומזיץ", "https://live.kcm.fm/19"),
                    RadioSubItem("צעד תימני", "https://live.kcm.fm/20"),
                    RadioSubItem("גרין", "https://live.kcm.fm/21"),
                    RadioSubItem("Top 10", "https://live.kcm.fm/24"),
                    RadioSubItem("חתונה", "https://live.kcm.fm/25"),
                    RadioSubItem("מנגינה של תקווה", "https://live.kcm.fm/27"),
                    RadioSubItem("מוזיקה אלקטרונית", "https://live.kcm.fm/28")
                )
            ),
            RadioItem(
                stringResource("radio_idish_24"),
                "https://music.yiddish24.com:5001/1",
                "assets/radiosIcons/yidish24.jpg"
            ),
            RadioItem(
                stringResource("radio_jewish_radio_network"),
                "https://stream.jewishradionetwork.com:8000/stream",
                "assets/radiosIcons/jewishradionetwork.png"
            ),
            RadioItem(
                stringResource("radio_geoula_fm"),
                "https://broadcast.adpronet.com/radio/8010/radio.mp3",
                "assets/radiosIcons/geoulafm.png"
            ),
            RadioItem(
                stringResource("radio_jewish_music_stream"),
                "https://stream.jewishmusicstream.com:8000/stream",
                "assets/radiosIcons/jewishradionetwork.png"
            ),
            RadioItem(
                icon = "assets/radiosIcons/kolsimha.png",
                stations = listOf(
                    RadioSubItem(stringResource("radio_category_kol_simha"), "https://broadcast.adpronet.com/radio/8000/radio.mp3"),
                    RadioSubItem("להיטים", "https://broadcast.adpronet.com/radio/8030/radio.mp3"),
                    RadioSubItem("שקטים", "https://broadcast.adpronet.com/radio/8020/radio.mp3"),
                )
            ),
            RadioItem(
                icon = "assets/radiosIcons/makeletshira.jpg",
                stations = listOf(
                    RadioSubItem(stringResource("radio_category_makelet_shira"), "https://music.shira24.com:5001/10"),
                    RadioSubItem("פריילך Freilach", "https://music.shira24.com:5001/2"),
                    RadioSubItem("רוגע Relax", "https://music.shira24.com:5001/73"),
                    RadioSubItem("שבת Shabbos", "https://music.shira24.com:5001/3"),
                    RadioSubItem("ווקאלי Vocal", "https://music.shira24.com:5001/8"),

                    ),
            ),
            RadioItem(
                stringResource("radio_toker_fm"),
                "https://broadcast.adpronet.com/radio/6060/radio.mp3",
                "assets/radiosIcons/tokerfm.jpg"
            ),
            RadioItem(
                stringResource("radio_chabad_org"),
                "https://stream.radio.co/sdfd68a101/listen",
                "assets/radiosIcons/chabadorg.jpeg"
            ),
            RadioItem(
                stringResource("radio_the_lakewood_scoop"),
                "http://janus.shoutca.st:8869/index.html/live",
                "assets/radiosIcons/tls.jpg"
            ),
            RadioItem(
                stringResource("radio_dance246"),
                "https://s4.radio.co/sde4a5043a/listen",
                "assets/radiosIcons/dance246.png"
            )
        )
    }
}