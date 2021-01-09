package com.example.webview_screenshot

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.os.Process
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.hd.viewcapture.ViewCapture
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    private val testBase64Html = """
        PCFET0NUWVBFIEhUTUw-Cgo8aHRtbCBsYW5nPSJlbiI-CjxoZWFkPgogICAgPHRpdGxlPjNEUyAtIE9uZS1UaW1lIFBhc3Njb2RlIC0gUEE8L3RpdGxlPgogICAgPG1ldGEgY2hhcnNldD0idXRmLTgiLz4KICAgIDxtZXRhIG5hbWU9InZpZXdwb3J0IiBjb250ZW50PSJ3aWR0aD1kZXZpY2Utd2lkdGgsIGluaXRpYWwtc2NhbGU9MSIvPgoKICAgIDxzdHlsZT4KICAgICAgICAvKiBHZW5lcmFsIFN0eWxpbmcqLwogICAgICAgIGJvZHkgewogICAgICAgICAgICBmb250LWZhbWlseTogJ09wZW4gU2FucycsIHNhbnMtc2VyaWY7CiAgICAgICAgICAgIGZvbnQtc2l6ZTogMTFwdDsKICAgICAgICB9CgogICAgICAgIG9sLCB1bCB7CiAgICAgICAgICAgIGxpc3Qtc3R5bGU6IG5vbmU7CiAgICAgICAgICAgIHBhZGRpbmctbGVmdDogMDsKICAgICAgICB9CgoKICAgICAgICBzdW1tYXJ5Ojotd2Via2l0LWRldGFpbHMtbWFya2VyIHsKICAgICAgICAgICAgZGlzcGxheTogbm9uZQogICAgICAgIH0KCiAgICAgICAgc3VtbWFyeSB7CiAgICAgICAgICAgIGJhY2tncm91bmQtY29sb3I6ICNlZWVlZWU7CiAgICAgICAgICAgIGNvbG9yOiAjMzMzOwogICAgICAgICAgICBwYWRkaW5nOiA3cHggMTBweCA3cHggMTBweDsKICAgICAgICAgICAgbWFyZ2luLWJvdHRvbTogMTNweDsKICAgICAgICB9CgogICAgICAgIHAuc3VtbWFyeS1wIHsKICAgICAgICAgICAgcGFkZGluZzogN3B4IDEwcHggN3B4IDEwcHg7CiAgICAgICAgICAgIGJhY2tncm91bmQ6ICNmNGY0ZjQ7CiAgICAgICAgICAgIG1hcmdpbi10b3A6IC0yMnB4OwogICAgICAgICAgICBtYXJnaW4tYm90dG9tOiAwOwogICAgICAgIH0KCiAgICAgICAgLmJ1dHRvbiB7CiAgICAgICAgICAgIGJhY2tncm91bmQtY29sb3I6ICMwMDc5ODc7CiAgICAgICAgICAgIGJvcmRlci1yYWRpdXM6IDVweDsKICAgICAgICAgICAgYm9yZGVyOiAwOwogICAgICAgICAgICBjb2xvcjogI2ZmZmZmZjsKICAgICAgICAgICAgY3Vyc29yOiBwb2ludGVyOwogICAgICAgICAgICBmb250LXdlaWdodDogNDAwOwogICAgICAgICAgICBoZWlnaHQ6IDIuNWVtOwogICAgICAgICAgICBsaW5lLWhlaWdodDogMi41ZW07CiAgICAgICAgICAgIHRleHQtYWxpZ246IGNlbnRlcjsKICAgICAgICAgICAgdGV4dC1kZWNvcmF0aW9uOiBub25lOwogICAgICAgICAgICB3aGl0ZS1zcGFjZTogbm93cmFwOwogICAgICAgICAgICBkaXNwbGF5OiBibG9jazsKICAgICAgICAgICAgd2lkdGg6IDEwMCU7CiAgICAgICAgICAgIG1hcmdpbi1ib3R0b206IDE1cHg7CiAgICAgICAgfQoKICAgICAgICAuYnV0dG9uLnByaW1hcnkgewogICAgICAgICAgICBiYWNrZ3JvdW5kLWNvbG9yOiAjZWY4MjAwOwogICAgICAgIH0KCiAgICAgICAgLmJ1dHRvbjpob3ZlciB7CiAgICAgICAgICAgIGJhY2tncm91bmQtY29sb3I6ICM3MzczNzM7CiAgICAgICAgfQoKICAgICAgICAuYnV0dG9uOmFjdGl2ZSB7CiAgICAgICAgICAgIGJhY2tncm91bmQtY29sb3I6ICM1OTU5NTk7CiAgICAgICAgfQoKICAgICAgICAuY2FudmFzIHsKICAgICAgICAgICAgcGFkZGluZzogMTVweDsKICAgICAgICAgICAgYm9yZGVyOiAxcHggc29saWQgI2VlZTsKICAgICAgICAgICAgbWFyZ2luLWJvdHRvbTogMTVweDsKICAgICAgICB9CgogICAgICAgICNjb250ZW50IGgyIHsKICAgICAgICAgICAgbWFyZ2luLXRvcDogMDsKICAgICAgICB9CgogICAgICAgIC5pbnB1dC1maWVsZCB7CiAgICAgICAgICAgIGJhY2tncm91bmQ6ICNmZmY7CiAgICAgICAgICAgIGJvcmRlcjogc29saWQgMXB4ICNlMGUwZTA7CiAgICAgICAgICAgIGJvcmRlci1yYWRpdXM6IDVweDsKICAgICAgICAgICAgY29sb3I6IGluaGVyaXQ7CiAgICAgICAgICAgIGRpc3BsYXk6IGJsb2NrOwogICAgICAgICAgICBvdXRsaW5lOiAwOwogICAgICAgICAgICBwYWRkaW5nOiA3cHggMCA3cHggMDsKICAgICAgICAgICAgdGV4dC1kZWNvcmF0aW9uOiBub25lOwogICAgICAgICAgICB3aWR0aDogMTAwJTsKICAgICAgICAgICAgbWFyZ2luLWJvdHRvbTogMTVweDsKICAgICAgICB9CgoKICAgICAgICAvKkltYWdlcyovCiAgICAgICAgLmxvZ28tYmFuayB7CiAgICAgICAgICAgIGJhY2tncm91bmQtcG9zaXRpb246IGNlbnRlcjsKICAgICAgICAgICAgZGlzcGxheTogaW5saW5lLWJsb2NrOwogICAgICAgICAgICB3aWR0aDogMTUwcHg7CiAgICAgICAgICAgIGhlaWdodDogMzVweDsKICAgICAgICAgICAgYmFja2dyb3VuZC1zaXplOiBhdXRvIDMzcHg7CiAgICAgICAgICAgIGJhY2tncm91bmQtcmVwZWF0OiBuby1yZXBlYXQ7CiAgICAgICAgICAgIGJhY2tncm91bmQtaW1hZ2U6IHVybChkYXRhOmltYWdlL3BuZztiYXNlNjQsaVZCT1J3MEtHZ29BQUFBTlNVaEVVZ0FBQUwwQUFBQTBDQVlBQUFEZmFEa2dBQUFBQVhOU1IwSUFyczRjNlFBQUFBUm5RVTFCQUFDeGp3djhZUVVBQUFBSmNFaFpjd0FBRHNNQUFBN0RBY2R2cUdRQUFBWFpTVVJCVkhoZTdaa3hVaXc3REVYLzBvaFpBREVMSUg0NU9URTVNVGs1T1N0Z0I2eGlmaDBLVjkybmtpMjcyejJQR2V0VXFXREdibGt0WDZ2dG52OU9TYklZS2Zwa09WTDB5WEtrNkpQbFNORW55NUdpVDVZalJaOHNSNG8rV1k0VWZiSWMzYUsvdWJrWnNvZUhoOVBUMDlQcDQrUGp4OFBsOHY3Ky90ZTluUU1kai9HVGVSd21lclhIeDhmVDE5ZlhqNmZMSTBWL1haeEY5QmlWLzFKSjBWOFhtMFFmVFFKYm1yZTN0MitoNjNVdkx5OC9QUzZMRlAxMWNZam9GUlgrM2QzZHo3ZVhSWXIrdWpoYzlGUjl2ZllTRDdZcCt1dmljTkhEbm10L0F5bjY2K0xzb3UrcDlKd0hlT056ZjMvLzE3VzN0N2ZmMnlYT0J0SGJJTDJ1OFByNmV2cno1OCszbjlMR0dNL1B6MDEvdmFMSGg4Yk0vMXZmV3VsNHMwWC8rZm41L1RyWm5ybUlsKzlwNzRYN1l6N1VGL2xsL29vZnpSLzlJbWJHNTNHNDZBbXdYRWN5V3JBZ1ZEUXR3eGNpcnFGOW1SaWJRR3Y0WTdGNTlJaCtwdUJCeDVzcGVrU2p2bXRHdndqaTBnTGlHWE0wSXZxWjhkVTRYUFJVMW5JZEZiVUdnbzhTNkZrdEZ1MHpzcEM4S3FLVGhsbG1DeDUwdkpGOHQ2RDZxdC9JNkYvRG50VmFwa0p1aVg1bWZDME9FVDNDWVlWYkliVFFTb3o0V0NCMks4UzROakVzS2cvdFU0eHIxU2VWM1M0SXI0SzBSSCtFNEVISG15RjY4cWsrZVpQR0hKVlkrY3RudnRkK1hxR2lyOWV2Rkl6aXl5dGlOZEhQakM5aWsraEhEU0dWNEQxczFZZ21XUk5FWWozVUgwYkNQT3dFZW91ekpYcGRoTE1FRHpyZVh0SHJGaE5yeGNuM3VvZ3grL1N6QXJYRnFlRDU4a1EvTzc2SXM0aWVHMjFObkNheDlmZ3IyQ1I1YUh2a2s0T1k5cmZVUkgrVTRFSEgyeXQ2dlQrS1JCUW43VnFsN1krS1dpU2lTbXNMbWpjWHMrT0xPSXZvaTQwRzEwTDllbWg3Tkc2cmtvUFhmcVRnUWNmYkszcUVWbnoxSGdCciszQmJjSHJ1Vzg5MW51aG54dGZESVh0NkVrRWZBdE1WaWRYZWtMUW8vaEF2WXJQN09nOXRqK0tsWGZ0YmJMc0tIaHROZWcvcVA0by9Zb3V2V2s2WXYvSWRpNzJINkVsZTJyQzk4ZlZ3aU9nVkhtOHFmQVRiZ2txQ3VLa09kdTlXTXc5dGorS05FbWpiUFp2NUZBUDFQWkp2ankyK2Fqa1ozWXFDTHBRbFJBOElJcnFlYW00cmFLOTVhSHNVYjVSQTI0NnhrSmxBL1R4Nm9HcWhZNDNtMjdMRlZ5MG5XMFN2dnBZUnZUM00yTU1QZ205VmRaNE9KSXZ0RW05aFJnK3lVYnhSQW0wN0F1ZWVpRnVmWXIwaTZFSEhHODIzWll1dldrNnkwZytnMTF2UmF5SXhLajZKcWxWT3hLYjlQYlE5aWpkS29HM1hWM1FzUW0zYmNtYnhVSjliOHEyb3I5NzRWS2hZUWUvM2lEMzkzdmg2K0NlVjN1NS9SMTZCUVNSUzBQWW8zc2hmMU01RWxqWXFQNHR5THpyZWFMNHRHdC9ldHlOMkxudnVkWW0zTnhaYnliVlNncmIxK05Za1loN2FIdm1NUkIyMTIrMVc3VmZpRWRUZmFMNHRlcWJxV1pSMjIyYUZPRktrYkc0OGdjNk9MK0p3MFNOd0RkQjdlNk8rYTcrY0ZyekRycmNOMHZhalJROTJZZThWNmt4ZlZuaXQzeFg0M3A2dmJINzFYcGxiVzhRS25pOVA5TFBqaXpoTTlQUmhCYXJnTVUvVWVoUDBKNmw2SS96UGRWcGgxTHg0b25hRmR1MXZpZG9MR2gvLzF5YXVCeDB2aXI4SHV5aUpqNXlXR1BucjVkaXJvdlMxL1hUT2FyNncybFprWm53Um0wUy8xYWpTSHZaUU1tcmU0VWZienlWNjIyL0xoQlRVenhaRFJCYTdMWXlzdFZlMmUvdGVhL21jR1YrTHM0bmVtd1NGVmV4ZFo2MVVBRzY0Zk9lSlM2ODVsK2pCYnIraXNXdW9qeTFXeXplNTh2cGI2MW13M0p0OWtsdmpLUjRkWkpXWjhkVTRUUFRsWmpta2xFZFVCSTlIUkdQM2JId3VyekVMdWtoWUNCYTkvcHlpNTE1VkNNUytCUjF2aTdXS0RIbEdORGJQQ0pMdmRXc1p3ZjB5eDFxRWlpL21DSFRyMG5QSW54bWZSN2ZvazJRckt2cm9pWDhPVXZUSk1GcDllN1p2K2hRbzFmOWZrcUpQaHRIdFc3UmRzV2UxdlZ1VEdhVG9rMkhzWVIzaDI0clBaOXVQejcrQkZIMHlETlU2ZW10ampVTnA3d3VObzBuUko1dmdQYjMzNDVOblBBbCtpK0FoUlovc2dqMDdvcllMZ01yTzY4V2VnKzY1U2RFbnk1R2lUNVlqUlo4c1I0bytXWTRVZmJJY0tmcGtPVkwweVhLazZKUGxTTkVueTVHaVR4YmpkUG9mUTRHSEpwSUc5cThBQUFBQVNVVk9SSzVDWUlJPSk7CiAgICAgICAgfQoKICAgICAgICAubG9nby1lbXZjbyB7CiAgICAgICAgICAgIGRpc3BsYXk6IGlubGluZS1ibG9jazsKICAgICAgICAgICAgd2lkdGg6IDE0MHB4OwogICAgICAgICAgICBoZWlnaHQ6IDQ3cHg7CiAgICAgICAgICAgIGZsb2F0OiByaWdodDsKICAgICAgICAgICAgYmFja2dyb3VuZC1zaXplOiBhdXRvIDMzcHg7CiAgICAgICAgICAgIGJhY2tncm91bmQtcmVwZWF0OiBuby1yZXBlYXQ7CiAgICAgICAgICAgIGJhY2tncm91bmQtaW1hZ2U6IHVybChkYXRhOmltYWdlL3BuZztiYXNlNjQsaVZCT1J3MEtHZ29BQUFBTlNVaEVVZ0FBQU5BQUFBQTBDQVlBQUFEN2NiYmdBQUFBQVhOU1IwSUFyczRjNlFBQUFBUm5RVTFCQUFDeGp3djhZUVVBQUFBSmNFaFpjd0FBRHNNQUFBN0RBY2R2cUdRQUFBQVlkRVZZZEZOdlpuUjNZWEpsQUhCaGFXNTBMbTVsZENBMExqRXVOdjFPQ2VnQUFCZS9TVVJCVkhoZTdaMEpXRlJIdG9ETlpDWWJnZ0lDaXJodmdMZ0xyc2pTM2REc1d6ZDdMM1RURGMyKzc5cWdnQ0tpVWNFRVpWTVFCZU1TNGt0aWtna3ZieVpPMkVRZDUwMlNONW1aK0NiTFpETlJZemJ3VHAxN3E1cEdhQVFFTlhyLzd6dmZwZXZVclh1N2JwMDZwNWJiVEdCaFlXRmhZV0ZoWVdGaFlXRmhZV0g1MVZOVWQyTHA5dXFUeXc0Y09XZUtrMWhZV081ays5R3poblA5RW15c2hJbjVxOE16T2t4YzVGL3BiUXIveW9Rcm8weDRjbXF5aytUN2llanp5dkNNcnl3OG9zbzhrN2VIU3RUbFUvSHBMQ3lQSjJIWmU2M25COFFWbXJuS3Z6VndFRkg2bThSWVJCUXlHQ1J3MUU3dkUwTm42ZmMyUVVsdk8wWnQzb0NMWTJGNVBJZ3ByWnExUEN6dCtCUk9SQTl0SlBaZ0xIY0tNU0pkRWtZZkp6bUtLVFBYeUxjQzBrdFc0K0paV0I1ZE9ESDVZVVljeVpkM0dnaDRuVW1Pa2gvbitjWjlhY1NSbFFkbWxaYXZFbVUyb1JEdUd1aUlvSENPMmlqUHUyWElrZDdRUjE2TE9aLzJWRGNzQlFucTVMS3laL0dsV0ZnZUhSelU2dDg2cVRiWG9WRHRkai9EUVVaZzdxNzgyaWs2djRJWHQyME96cTZCbDFxcXQ4QS9MZzk1bWgrSjBjM3lWbjBjblAzODBnM3l2TkM1UHJIdlQzSVFFeU9pRmdURXQxc0lrbGtqWW5sMFVLdlZ2MWtyeXpuUk43YUI4RXRFbWZCazM5cEpNdFBWbFMzUDRhdzZzWldrTFovdHJickpuQytpTE55alBsWVV2amdOZEU3UlcwUUdqdUlQSVYwZjZaRjNhcHZsSUhtR1BwR0Y1ZGVPUjJKeGs4YmpJTkZEc2pRNDVZOTVCNW9XNFN6RFltdlZpUlZXd2lSa1JJd2hJb05xeGFvSmd1eTlKaWlFK3oyNWpqRXlvdm44K0tleG1vWGwxNGxEbEZwZ29BbXhtRUcvaVlzc0RhdEhqR2pyZ1JWbXJwSFh3UkNoekNYQktUdXdpbWFPbCtvZzZNRGJXUWNtdmRkMC9qd2J6ckg4T2trdXE1cyt5Vkh5TVJPNmlTaVlxcmIwajAvQjZsRXp4MU5sYThTSm9MMFpUQ3A0cDVhdXdTb2FFMWRaSmUzdDBQVldpVEp5Y1RMTEE2YnNhSXVEVFdDQyszT2JSRWhDM0ZHN29QL21KMngxYnpqNzdpeWNqWVV3elUxWlFUd1BHTkY4di9oZFdIWFB6UFpSN1diQ05SRTExVTN4QjV4TXMrdG95NVNabnRFZmdCNk5qVzdZQkNlWVlkV1FDTExLV3RmTGNuckhUQ0x6cExob0dydUliT3NOV3ZvTjh0d2YzV0tMRjJMMVBlR28yT3lCeWtObG9uSlIyUTdLTFRmVkIwOWFnTTRsZnR2cjlIV1IzazZjZFRXMzR0Z00rcVI3WUVOazdtNjQzbnE0WG1UdUx6SEYxZjA2TVlKbmNvbURkVkRDNGZtK2NiMGthbUNPU1BEeUJVNi9qU0tHZjl1S01rdEQxYnZuNHRNZlgveFRTMmNaTzBzMU0yNXpmRlIvRlRZM1A0blY5NHd3UmozUjNGMzVMVk8raUpJVlZQVHpiSXVEMHVaTmNoTC9ESHBMWWVMLzRPUWg0Y1lXZkRuUkFaVTNScUx2RUI2Qmk2YVp1REhNdWw4ZXFCZHYxV1dzSGpWMllXcURPZDR4dDBoZFEzMmdNSmVDclZDZ3R4WGx1SUkzSnBNNHFLRlcweWVPRWtYeGkydE1lSEw4YkVYbzJjYjhDYXMwYkl6SW1vdkdwTzlCMUFGNUdHSHVqOXhIZitsTE0zU1c5Rm9KRXNzdDFnb2UzL0I3ZVZqcVFhZ002RjFRaFZEckkzT1dZOVdZSWNqYXRST3VBWk1TczcxVTUxSFNFNHlHd1ZhU2NCTDB4aHpwYlpqMnhzazY0Y1lVZkFuM3Era2g3MUgwTnc1aVFBTWFqNGhhSTg3TXcxbEdoVTFRY3FNK1hsZ200ZkpVTFFPaUtPcUpwYUdwbDhpMVRYaXlYcytFSFRQcGswZUJ0VERwVmViZXcyQk0yOE5SRmRoaUZZMXYyazZWa2JQMEovSWRpY0VZT2tzcEZCbDhnY0x1dG9YK0NXMXpmV0xiVUhxYnVadmlDN0ttcDEzL016eWp2N0VXSmc2NzNWUjJkdjR1cXZBRnUyZnR4WFk4VllGUXZHVmZtUUg2MjhJanlpNnY0ckFWenZid0E0dVpacTd5VDZBU1FGQ2xOV0xWbUtKVVZ6NW43cWI4RHE2QnhscFVjZlVKVFJoUjFlNjZxT1ExbDJ1R3pzdzlXQW9TNjdGS0o0d0JNVDMxc3RDMFcxbjdqMlJuN0c4WXRXUS9YOWZQYU1HQVNHT0NvNEVqMDlpUmdmOGlMNnE0cTRFUGhyTks3VXQ2ZVJTdTBtV0RhSHNnUUtJdTU4TUVEdWowVU43cGJvcGFyQm9SR3lKejFrNTJFaVB2dzF4empUVHJqMWhGNDVaVVZFaXVRd3gycHFmcTg3WHluRzJxWFRVejFMV3RBNVlYSk9yYVoyS0xEeTYwQ1U0c05lY3JQNGU2Z1h2RUJuckRuQzliZ2JNT1Nzam1QZXNXK01lVlQrTkgzcGlNT212YUFPMlo1MGlMZ3hqR3lqMHpQYVArenk0OEl3TjFwc01LNlI4WWNTVTE5aWg4b1NzUVp1QTJ5dFdqYWh6RHdVcVlWRXdhalowMGF4K2tuYmtrVzFUVDZmYnRvWFlPdFZ3V1NOK0hDVmQrdmF5cGFjaVFnSU1NaURTTXRSRTUzK0RrTVVQYmdFeGNaSlJUZEg0SFdSTkQzK01Tempac0hGQVlheGtRZndzYUROVHp5dkMwYXRKNzMybEFnTG1yb29wOFA5UXdmNG5iZnRBWnE0WkZMV3I4Rmg3UmJ6QmxJSS9pSlBsZWUvRjdZV0NDN1dSbnhuamdQaUR5V0JXZWZoQTZPcHpscmtCZVMwRlNJVExTWDZBY0VBdDM1UTJoZXVlQXpjUFJwUWRNVjRzeXpxTHJhQzNPTTk5dm9QVHBwcmtwYmxrSHBVZ3JLeXQvaDR0NnVKamxvVkNSRzBieDhWOXg4cmlRaG5wNU1rMXV6SXM0LzlKbGllWGhUbzlyVmNoNFFCVGxYclFPZXVuRmdVbERianhsUEJDeldYVnRSTzQ0RzVDY3luN3hHQWMxOUkvSnczZU16bGZqck1OaXRUampLRGwzbmsvc0h5YzdoUzhqbjdWRE9JSi85aTVMcHJFeDkyQWxUT3hTVTlSdnNQcXUrS1R0REdXOEhYT04yVjdSdTdHS3hwUXJ2OGlVeld5NTRzVGtwOE1pT2xhUENKZll3Z0JrT0greENVNVNvNDVpZ1BHc0NzbTJSTjhaajRIN0JIM3YzaWs4MlVkMjRzeVA3Q1B6UHJJSlN2M0l5RG5pTXlPT3RGOCs4UDZMaGNtbnJZWHFwM0NSRHcrTzBWdmVKVGU2TkNSbEcwNGVOK2I1eGIwUDExb2NLcVpxMmoydUUrTUJLWG5OblRZZWVLaWJGSnRwRDZVTGJRKzBaanc5RVBMTzhKckcxcnJUcTFhRXBYT1lVSTQycWx1THZHWDZPUHVRK0dlWCtFNm1ReVY2UjhkUDY4SlRUZlUyaFM0aDlUNllCd0tXaHFUV01ubEU0RUVvMUdsd3NHcEkxT3JtcHhZR0pIeEd5cC9DaTdqaGxWQ2tDWVdROXhjU0haUzlPREM1RWNaZVdEMm1TSW9xTGMxY0lxK1I2MEdkR25ObGYxc3NTQkxCUm1XY1RVTlQwL2xuN2NKejV5RFBWbzNHV3RmSk13WlpKRWc0clc1dWZyaU1hS1pIMUdVSVRlQ0xCV2FWdWVQa2NRTTE5am9iY1NDMXQ1V25NUnhHdU5UQi81WVZvN2lhM3ZXTmVxRWh4Mkw5UGREOU1TQTB4SDlpU1VqcU9kTHdabmdxL3d0bjF3ay9mdS9UcU1GMHdlUUpsSWZHZC9RTXBONG02VjBOS0dWWHJhVXBUOWFyVDA4aGl5ZzBodXdjanBkWUxVb1gwZmVPNzNPRElxOWZaelRiSitZVmN1MHBYT25QQ1VXSHhtMmNnY0xkZDBtb0NwTVB5TEJmbnVDZy9pMVdEOGw4UWJ6MURNOG9iSHpNdUhGbGFGcS81WVlIemtUN2NHUkFkQnpjZzVQR2xmelRYcGt2bk9mZVlUd2NxcnFkMTlwOFJmZ1V1aGQ2VFdobGVNWVArSlJCMFRZZ05HYnFtV2d2N2hpV2JCSjNUTm9ZZXRlMUMyMERNa1VoSEdOQUtGeEpWaHVadTBmUlUrNmdkMUJzRWRFbjZHQzZoN0lBSGo3a1h5UkkvQ2RaSHREMlFJT0ZjSVFWb2VuYlNUNElmNjJDRXQyd2FsQ0V3dVluelZ6ay8wL09tZVlXK1hYVy9sUEdXRDFCaWNZU2l3SVNOZUdVS1MvaWJhd2FjNXdWQlU2TThURDFPTjB0NmpYWXFJelZ3MkpEWk41aU5GYjducWxEbVBLWGY5dzhoa3NzOXd4alFIVFBPSzRHOU9xSDhVL1hkL2cwM0drNHRMUnhyOEY0Q1BLaG5ob1pFQnFQZWNjTWFVQk1DTWMwZ3BHSmlOSnpFaS9HeGVoa2NBL0VzQ284UTA3S1E0My91MVZLOWFBRDcyWEJNWXVObkpsNEhvMHplc055OXp0aVZaOEJvZkoxZVNCQXFOd3hDUWJTcEJlZjV4ZjdSWHBWbGM3UUVkVkxJZkUrY0k2VklMNmZnUXZWNnFkbWVVYlRlcWdMeHlqMXVCblFXbWxPUFhPZE1PVHBJbTVIbDFTTmFEOGxZYlU0YXgvOW5aQW5oaGxjVHV3MlRUMCtjSWdIUW9QMnYrT2tNYWV4MjgranRzUDk2LzZHUTd3UWx6cDVTWktFczZLR0ZVWjdJRU5uNmJBOTBFamxYZzBJUUQzN20wempDS2NXQ3VKZndja2FWaW1WdjdQd2lLTEhleURXZ3NRWHNJcG11QVlFTEFsT1RtTEtZWHJoZGRMc1JLenFoN3EyZWVwc2IxVXZ1YWFGbS9JcWhKQllUUU1HTk5OVGhjc0toOG1EY1RNZzFMYnFpVEhEaG1HY1BHSk1PUkZ6WWFhWWRDSjJva3daVmoxNFVBOUtHeENxMEFhY05HWTBYZzVlZXFUTHR3c001UkNTNm5idStlTVhRN1NNaDBNMWRBZG80bk9ZdWthR1E2OUp6ZllhMmdOcEd4RHluamYxN0VWQnd4VkRybkFTTGtZbnVrSTRRbkRlcmhWb25IWWJ4amF3bHNKUmJRbkNLaG8wTU4vT05CNFJOWlVmK1FsTzFrQU1DQnJGVUNFY1VMei9zUEZDdi9nZlNLTkhIdWxyZnJ6YUFLczFMQXFJenlkbHduakRRWmtYamxVYWhPcm1weXlGaWZSOVFkNzVmbkhqWmtCcklyTHBhNEFzOGs4WXRRRjVKRzJmaThKMCtsbURCR2FXSGNHcUJ3OHZwb0FPNGRiSmNuN0JTZmZNeVVzUmdwcDIxMVBJWUdnak9kemxkZldseTRyVXVrNnY0OFJ3YUhrMy9NUFcxcjZZR0ZiY29iY0hsMjhsU0JwMkNMZEdtbjJmSmhINll4T2Nvb2E2Z3dacjdxNjhGcWJlU3pmcTlaS2M1U1k4R2JvM1ptSFFLaWpPbmo1Qmk1RjRJTUFsZGxzOHM4V0g3dXlvMmQ0eENWaEY0NWRhYWpxTnIyRFdXT3hoZDN2eVAzWE5XQmx4cGE4UUF6SnppZnhzcE9PUzRUTEhLd2JQOE5JVElLTTJvQVgrU1hNbk9aRUYzM0RLUGFtb0dLc2VQRlA1c2hpNHFZWCtDZmMwQm1wNVA4V3k0VUpBV2sySGF6Y1lSM1VIajZydDVMOVIyK0cyNGVXTGNYTnFPdDFidFkwbitiQUh0VkdaZlFxZlRyTk91dG1XVlBoOHYvZ2g5OFRkejFrNFV4MEdoSGpDd2wzNWQvSmdWNGFsditZZ1VUK3p3Qy91S21tZ3k0SlR6dUM4L2RBMm9MdDVJSUtwYStSRmNxMHB2SWdmNGtvUGFoWkdsNGN3MjdGQW9MRlpCU1FPdW1FVVdCR1dsa3Z5Z2dSbjd5N0VxakZsbG1jVUhnT2hFSTRyL1FMcUJxdEdoRmR5Y1RZOEN4Q1lTSm5sRTN0L3gwQ3QvMUJQMWpYUEw4allZUWRmRU4xY3orRTMyelN6TmNPaDZVS1k5Ykh1a1BUcWRwY3p5Q2g2d2VQVWRYcis0M2gzOElINjk4S2dOMzdpMklXZzlkWHR2TSsxalVkWjRZa2VjaGlsNXlDS1owcGlzQWxKMlFNTkQ2WnRrUWNhY3ZzS1kwQk1wZDYvYWV5QmhHM2U3MmZnSUtMSEhaT2RKTGZYU0xPNlNhTXhkSko4cXE1c0huUWYyMGc5RU9Dc0t2Q0NjSkV4VGhHMVhwNTdHdExqdDFldE1LSzN4VEJHYStvcUh6QW0wK2JReTYzelo3aEgwZnNTUVF3MmlUNWJFWm83NmxjVWdySjJMVndXa3JJcnB1elFmSnhFNDVPMkE0M2RtSHNDV1N2Tkd2RU9mNWlnZ1QxNXBCd1Ric1NQWlUydlRNZnErMFBMbFlTWjlSZThPeHN2Qkg2TUdubFpZNWRRZHFqRDJhNnBXMjVYMVBpODJXeWZ5QnN3U1BOTzN1NkhUK2xIMC9ua1p5RXZuSFAwWXRBV1ZNYSs2ZzdYSDVBeDlCeHE1MTVENFZvYjhqU3BiMzJndGlNaDJhc2Y4cDgrZWtHd0c0MTNiaFBEcWVsd29XSVBlcitsNzhCc2llR284alVHQlB1dXByc3BQMmUyeTZCN1NTMk94cXBCWVVJNHBpSGRyNFZVckJxQXJUampPR204bWdaakg5N2pvRlFQR0lNUVJtTkEwQW1pY1E0eVVPWWF5SmgrNWlac1hiQTBMUFZ0VWhld2loK1NVMmFIVDlISjh0RFViSElPaUFsWDlpbnlvQ3V4ZXRqWWlqTTNtZkhrMTZBTVU1NnNaNTBzdDdDeStVMTZqQWxyVnVadWluOHo5VUo3SVhoMW85K20zYUVBNDFrU0ROK3RyMTduK2NiMGV5bnp2dEZNQ1o5ODljTzBlVWM2ZmJQck9qeGhPdmxpVFlmYlQraDQ4NFh6bko2S1A2RHhTaHYvVm1OMzBIV1F1azZQNjRmYW5aRndybGUxY1c1V2R5RHYwdUgrTTBxN2VQUkNRQ2RLejRaSmdxYnpBN2V4SDdzUXNQNUlsODhYeEhCbzQybDMvZkxrbFNpQlUzUStjdXZNUTl2VGVGWVRaaXdPVExVbEd5d05uYVE5aVR0cmg5elpxeDNDMlVuR09ZUWJaQkpCbTVpZHRWT25jS1gwMmd1WktVSmprQ0ZEMEpGTUltZ1RVM0xJblV5TlF4M084b3kreEd5UFlxNXJKVWdjMHZzUTFLMnR2N1VXSnI1Rm5nV2NPOWxSM09zWWxmKzJXMUxoa0Q4N2R1VGNPVDN2NUNLbjFhS010NUVSYTdZYndYR1JJUDVWS0J0bm5iQTBKSE1qZUdaeWZ3WU9ZYjJiRkZ2T292R2lOYzR5Z1BMbTFvbExnMU9kbHdRbGZRdGxranBGbnVpVHd2cVQ5RzlyUEJTYzdwWk0zdC9HTVc2ODdHWDI4bDlpODZzNzNQSVB0WEd3T09YWGR3WGtuN2tTR3d0NVFNNWVWaG5pVXdmbHpKVW82L291M3plcTJoaXZnNHdOcEFlTmkwNDFYeEZPaER3TEF4TC9URmVHUi9UM1NtWGZCa0UwS01hcjQzU2p1SXFUZGFMdGdaRGgzZGF6RC84S0JKVkIvMUxxYUNULzBIRk43emdTRHdTZ2NDcUk2UUJFMUJTdTdLWlA1cDdaV0RVb28vRkFBUEpDdjVudkU0ZC90Nkt2OGRQMTRDQytXVnJmMHU5MWhhRlloZXAvY1VqeTd5ZHE3Wm1EQ01EQVVkUTd6VTN4MVF3UFZmbDBOMFcrclNRejMxYVVtVC9kTFNvZmhjdnZUSFdSZndmck1jd3ViT1k4K0x4YW5IRjJzQWtKYTJHU2tubmZqTnd2dlRXcDF5WXcrUk05aDdEODVhRnBhaWgvbmw5cy9seS8yR3JrdGI2YmhMZDBrZTgyamEvNDErcWdsQm5nMVk2KzFHb0owbmptRFhNb3Y2V2w1Ym54Mm9wMFgyaTZHRDZudXAyL0h4bmVUVzJ2VTl2Qi83U3hPeUFRWjV1dzU5aFpLeWFHRDZkUUtQSjduRHpCVXBpd2lvd2pvTExXUldUdnhDcWRhSHVnaWZTUk9aY3BBOExBa1VvNFZWQjFRaE0yanRTQVdsR3Z1OEF2L25XWUtiTVdKRWJoWkoyTTFvQUFGQ2JOTXRSNmg0ZDhieFRlN01GWmhrMXo4NVdubGdTbnhCbzVTZWplSGhZcisrcVIvSDNuWi9JM0kxUDVpbXQya2d4NVpXV256aDNUZ3ZReUlUSU0zT24xbFVPOEN6bmVxWWYyZ2pyYjF5SjM3S1hmMkQxeS9Kek5yb3JHTTdzUE5CV1hWUnhyM0ZQUkZMU3J2TEdxdnZtTlNQcEN2eFphT3RYUDFYZjZyYTNyOG55OUdvMXR0QTJudW9QM1hYMlhYL0hyVjlSR09Edk5HbkZXQVZNNThEcEROdDNJWU1GeG9YL0N2MGlGb1JqKysrRDBFcnBuR1FwK1F0R1h4cHdJYWl5bFVNdUFqSndqck9HM0hHQk1ZWUVHM052dllrQ0FhNVI2dHBtTHZCRzJ5K0FrblV6bVNKZEEyY1pJVUdoQzdheDdlZGdHQkt3U1p6YkF1WENQY08vSVkvekFqMmVtMFVmRFdybmFDSlZaWWVZUytUbnN6aWJQNmM0R1RmNkdNQkpkODFPYjRLUkNRWEpaditlc0M5WDJvNGJjdUszN1lLd0xMK3oxbGNlVXFXMU04SkxmQXYvNEt5NXgrVUYzYnQwcDN0ZEFyN21WbE5mTGRyOXdmTWZ6THphZE9WQjlwdCswL2tOSjA5WGtaMDlkamwzWDJDMnNxT25nLzRzWURCb1AwY2VhZHY3TjJuYTNiZFVYQkNiNEZBMUNvZkJKRTU3c0NsUVVpdmwvSXBzaTEwaHpta2dJQVJXM0xDU2wzNHE5THRMM1ZDM2l4eGRaajZVY1Bmc0hUWWdLSy9nazNUdTF5THEyZGVETFpmZUNkdm1DMUZKcmVIOEhxNFlGL01nL09SL0VSYlh0bm44N0FZQzFvK1ZocWRZK0tUdTNvWEZkT1hwZVdoSldQc3NqcWp3a2IyL3V4Z2kxOVVoZXI5QUcza2FGMzV3SXlOaFpNSTB2cDh2V1IyVkQrWXNEazh2NWlZVkNWQ2NEZnJpVFVMTHZpQzl6UE9wYmR1RFl2dnFtTjd4TDloNVYwc3F4NU15ZlpVNVZiUzZLMDFkaWxyZDBLb2Y5b2hUaHpGL1Q5VnV1S0djZTd2UlZORjRNZXFlMjAvVXFNUm90K1FXTmNUNm9iWGNWbmZ1Yld1ZS9ObGtqemZZbnZjMTA5QkFnelpBakNrS2ZOVnRQcHJ0SFhjdCt2dkhoZmdPUjVmR2g1WDNsbENNWGZONkhobDdUN3ZvdEd0Qy8wOWdOaHVDbU9QZ2VSMXlOQkk0SE81dzB4NFl1UVI0NjU1MUQ3M0hlT2R6bDFhdTk3YWE2ejJoNlVOajJRVU5Yd09HNmJxKzc3aXVMMzd2MzZXbHVTbnBmR016MFpKWTNiUEpQTDMzUndDSDhOaE56MHpOdnR4ZDR4K2hjL0dOaGVXQTBYQkRJcTlwNHg5RjRwWWNZZzI1eHZ1TXpZMEIxblY0M3E5cTR4MnM2M0VKUFgxSXN3VVVQQzVmWXdoSXdFZ2pSbkdPMnRzLzNqVHZHZUIwY3VxRVFicnA3WkNiT3pzTHk4SEs2TzNGeVE1ZC9FQnEzQkoyNEpENXlwTXZyRkRJUVJqcmd5RDExL0dMb3FYM25nZ3Rjc3dUVXlrZ2g1UmlUK3U3ZStsZDF6dHNQUlZEZUhpc0RCOUUzWUN3d3EyWHVycUFOUnpOZ1JNYkRWUlc4aHJPenNEdzY4R0lLRWd5ZDRmOEN3UTVnSmJVaElxY2hJR1AzWFVNMkF1eDlNblNXdk1sNG12NmloOHFFS1VvVFhrUUd6czdDOHVnUnNubVA2elMrZ3Q0d0NWNER5ZTBaWHRIL2F4V1lkSEN0T0VmTWpTbmdGTldlNWhSVm5VREhFeHhsMFl1Y1NZNFNqcjFpODc0Wkhxby9EV1k4c081aXpKRitFNWhkbG9vdnc4THk2Rko4K0pTeFRWQlNoUkZIcW5rblJUc0VneC9hZ0ZWNDhDaXdyYVJ2WHhnenpvSFBUQnJ6Q3pBV0hzcm03VFduUnpTT1ltSDUxUU9MYlRPOW9sK1k0aUxYL051U3Z1UGdRb3hwQ2s5MjI5UTE0aVdPSW84MUhKYkhHK1dPeWtudThZVzVLOFBTTDA3aHl2NE9LK0dNd1lDblliYlh3QXRsNXU2S0x5d0ZpVzFMUTFOVDE4dlNoL1VUVUN3c2p4V3djbTByeWpMVzU0aU05VzJSd0JFSnBGVzIzUDIvMmJHd3NMQ3dzTEN3c0xDd3NMQ3dzTEN3c0xDd3NEd2FUSmp3SCtRZG5TSW1qazN3QUFBQUFFbEZUa1N1UW1DQyk7CiAgICAgICAgfQoKICAgIDwvc3R5bGU-CjwvaGVhZD4KPGJvZHkgY2xhc3M9IiI-CjxkaXYgaWQ9InBhZ2Utd3JhcHBlciI-CiAgICA8IS0tIEhlYWRlciBTZWN0aW9uIC0tPgogICAgPGhlYWRlciBpZD0iaGVhZGVyIiBjbGFzcz0iY2FudmFzIj4KICAgICAgICA8c3BhbiBjbGFzcz0ibG9nby1lbXZjbyI-PC9zcGFuPgogICAgICAgIDxzcGFuIGNsYXNzPSJsb2dvLWJhbmsiPjwvc3Bhbj4KICAgIDwvaGVhZGVyPgoKICAgIDwhLS0gTWFpbiBDb250ZW50IFNlY3Rpb24gLS0-CiAgICA8c2VjdGlvbiBpZD0iY29udGVudCIgY2xhc3M9ImNhbnZhcyI-CiAgICAgICAgPGRpdiBjbGFzcz0icm93Ij4KICAgICAgICAgICAgPGgyPlB1cmNoYXNlIEF1dGhlbnRpY2F0aW9uPC9oMj4KICAgICAgICAgICAgPHA-V2UgaGF2ZSBzZW5kIHlvdSBhIHRleHQgbWVzc2FnZSB3aXRoIGEgY29kZSB0byB5b3VyIHJlZ2lzdGVyZWQgbW9iaWxlIG51bWJlciBlbmRpbmcgaW4gKioqLjwvcD4KICAgICAgICAgICAgPHA-WW91IGFyZSBwYXlpbmcgTWVyY2hhbnQgQUJDIHRoZSBhbW91bnQgb2YgJHh4eC54eCBvbiBtbS9kZC95eS48L3A-CiAgICAgICAgPC9kaXY-CgogICAgICAgIDxkaXYgY2xhc3M9InJvdyI-CiAgICAgICAgICAgIDxwPjxzdHJvbmc-RW50ZXIgeW91ciBjb2RlIGJlbG93Ojwvc3Ryb25nPjwvcD4KICAgICAgICAgICAgPGZvcm0gYWN0aW9uPSJIVFRQUzovL0VNVjNEUy9jaGFsbGVuZ2UiIG1ldGhvZD0iZ2V0IiBuYW1lPSJjYXJkaG9sZGVySW5wdXQiPgogICAgICAgICAgICAgICAgPGxhYmVsPgogICAgICAgICAgICAgICAgICAgIDxpbnB1dCB0eXBlPSJ0ZXh0IiBjbGFzcz0iaW5wdXQtZmllbGQiIG5hbWU9ImNvZGUiIHZhbHVlPSIgRW50ZXIgQ29kZSBIZXJlIj4KICAgICAgICAgICAgICAgIDwvbGFiZWw-CiAgICAgICAgICAgICAgICA8aW5wdXQgdHlwZT0ic3VibWl0IiBjbGFzcz0iYnV0dG9uIHByaW1hcnkiIHZhbHVlPSJTVUJNSVQiPgogICAgICAgICAgICA8L2Zvcm0-CiAgICAgICAgICAgIDxmb3JtIGFjdGlvbj0iSFRUUFM6Ly9FTVYzRFMvY2hhbGxlbmdlIiBtZXRob2Q9ImdldCIgbmFtZT0icmVzZW5kQ2hhbGxlbmdlRGF0YSI-CiAgICAgICAgICAgICAgICA8aW5wdXQgdHlwZT0iaGlkZGVuIiBuYW1lPSJyZXNlbmQiIHZhbHVlPSJ0cnVlIj4KICAgICAgICAgICAgICAgIDxpbnB1dCB0eXBlPSJzdWJtaXQiIGNsYXNzPSJidXR0b24iIHZhbHVlPSJSRVNFTkQgQ09ERSI-CiAgICAgICAgICAgIDwvZm9ybT4KICAgICAgICA8L2Rpdj4KICAgIDwvc2VjdGlvbj4KCiAgICA8IS0tIEhlbHAgU2VjdGlvbiAtLT4KICAgIDxzZWN0aW9uIGlkPSJoZWxwIiBjbGFzcz0iY29udGFpbmVyIj4KICAgICAgICA8ZGl2IGNsYXNzPSJyb3cgZGV0YWlsZWQiPgogICAgICAgICAgICA8ZGV0YWlscz4KICAgICAgICAgICAgICAgIDxzdW1tYXJ5PkxlYXJuIG1vcmUgYWJvdXQgYXV0aGVudGljYXRpb248L3N1bW1hcnk-CiAgICAgICAgICAgICAgICA8cCBjbGFzcz0ic3VtbWFyeS1wIj5BdXRoZW50aWNhdGlvbiBpbmZvcm1hdGlvbiB3aWxsIGJlIGRpc3BsYXllZCBoZXJlLjwvcD4KICAgICAgICAgICAgPC9kZXRhaWxzPgogICAgICAgIDwvZGl2PgogICAgICAgIDxkaXYgY2xhc3M9InJvdyI-CiAgICAgICAgICAgIDxkZXRhaWxzPgogICAgICAgICAgICAgICAgPHN1bW1hcnk-TmVlZCBzb21lIGhlbHA_PC9zdW1tYXJ5PgogICAgICAgICAgICAgICAgPHAgY2xhc3M9InN1bW1hcnktcCI-SGVscCBjb250ZW50IHdpbGwgYmUgZGlzcGxheWVkIGhlcmUuPC9wPgogICAgICAgICAgICA8L2RldGFpbHM-CiAgICAgICAgPC9kaXY-CiAgICA8L3NlY3Rpb24-CjwvZGl2Pgo8L2JvZHk-CjwvaHRtbD4
    """.trimIndent()
    private val testHtml = """
        <!DOCTYPE HTML>

        <html lang="en">
        <head>
            <title>3DS - One-Time Passcode - PA</title>
            <meta charset="utf-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1"/>

            <style>
                /* General Styling*/
                body {
                    font-family: 'Open Sans', sans-serif;
                    font-size: 11pt;
                }

                ol, ul {
                    list-style: none;
                    padding-left: 0;
                }


                summary::-webkit-details-marker {
                    display: none
                }

                summary {
                    background-color: #eeeeee;
                    color: #333;
                    padding: 7px 10px 7px 10px;
                    margin-bottom: 13px;
                }

                p.summary-p {
                    padding: 7px 10px 7px 10px;
                    background: #f4f4f4;
                    margin-top: -22px;
                    margin-bottom: 0;
                }

                .button {
                    background-color: #007987;
                    border-radius: 5px;
                    border: 0;
                    color: #ffffff;
                    cursor: pointer;
                    display: inline-block;
                    font-weight: 400;
                    height: 2.5em;
                    line-height: 2.5em;
                    text-align: center;
                    text-decoration: none;
                    white-space: nowrap;
                    width: 35%;
                    margin-bottom: 15px;
                    margin-right: 15px;
                }

                .button.primary {
                    background-color: #ef8200;
                }

                .button:hover {
                    background-color: #737373;
                }

                .button:active {
                    background-color: #595959;
                }

                .canvas {
                    padding: 15px;
                    border: 1px solid #eee;
                    margin-bottom: 15px;
                }

                #content h2 {
                    margin-top: 0;
                }

                .input-field {
                    background: #fff;
                    border: solid 1px #e0e0e0;
                    border-radius: 5px;
                    color: inherit;
                    display: block;
                    outline: 0;
                    padding: 7px 0 7px 0;
                    text-decoration: none;
                    width: 40%;
                    margin-bottom: 15px;
                }


                /*Images*/
                .logo-bank {
                    background-position: center;
                    display: inline-block;
                    width: 150px;
                    height: 35px;
                    background-size: auto 33px;
                    background-repeat: no-repeat;
                }

                .logo-emvco{
                    display:inline-block;
                    width: 140px;
                    height: 47px;
                    float:right;
                    background-size: auto 33px;
                    background-repeat: no-repeat;
                }

            </style>
        </head>
        <body class="">
        <div id="page-wrapper">
            <!-- Header Section -->
            <header id="header" class="canvas">
                <span class="logo-emvco"></span>
                <span class="logo-bank"></span>
            </header>

            <!-- Main Content Section -->
            <section id="content" class="canvas">
                <div class="row">
                    <h2>Purchase Authentication</h2>
                    <p>We have send you a text message with a code to your registered mobile number ending in ***.</p>
                    <p>You are paying Merchant ABC the amount of         xxx        .xx on mm/dd/yy.</p>
                </div>

                <div class="row">
                    <p><strong>Enter your code below:</strong></p>
                    <form action="HTTPS://EMV3DS/challenge" method="get" name="cardholderInput">
                        <label>
                            <input type="text" class="input-field" name="code" value=" Enter Code Here">
                        </label>
                        <input type="submit" class="button primary" value="SUBMIT">
                        <input type="submit" class="button" value="RESEND CODE">
                    </form>
                </div>
            </section>

            <!-- Help Section -->
            <section id="help" class="container">
                <div class="row detailed">
                    <details>
                        <summary>Learn more about authentication</summary>
                        <p class="summary-p">Authentication information will be displayed here.</p>
                    </details>
                </div>
                <div class="row">
                    <details>
                        <summary>Need some help?</summary>
                        <p class="summary-p">Help content will be displayed here.</p>
                    </details>
                </div>
            </section>

        </div>
        </body>
        </html>
    """.trimIndent()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        captureWebView()

//        val bitmap = getBitmapFromWebView()
//        saveImage(bitmap)
    }

    private fun captureWebView() {
        // Prepare webview
//        val scrollView = ScrollView(this).apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//        }
//        container.addView(scrollView)
//
//        // Remove webview from container
//        container.removeView(webview)

        // Put webview in to scrollview and change webview height to wrap content
//        scrollView.addView(
//            webview,
//            ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//        )

        // take screenshot
        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                Log.d("main", "Load page finished")
//                val bitmap = takeScreenshot()
                val bitmap = ViewCapture.with(webview).bitmap

//                saveImage(bitmap)
                Log.d("main", "Successful")

//                // Remove webview from scrollview
//                scrollView.removeView(webview)
//
//                // Put webview to container and change webview height to match parent
//                container.addView(
//                    webview,
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
//
//                // Remove scrollview from container
//                container.removeView(scrollView)
            }
        }
        webview.settings.javaScriptEnabled = true
//        webview.loadUrl("https://dashboard.omise.co/signup?locale=en&origin=direct")
        webview.loadDataWithBaseURL(null, testHtml, "text/html", "UTF-8", null)
//        webview.loadData(testHtml, "text/html", "UTF-8")
//        webview.loadData(Base64.encodeToString(testHtml.toByteArray(),Base64.NO_PADDING), "text/html", "base64")
    }

    private fun getBitmapFromWebView(): Bitmap {
        // Prepare webview
        val scrollView = ScrollView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        container.addView(scrollView)

        // Remove webview from container
        container.removeView(webview)

        // Put webview in to scrollview and change webview height to wrap content
        scrollView.addView(
            webview,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        webview.settings.javaScriptEnabled = true
        webview.loadUrl("https://dashboard.omise.co/signup?locale=en&origin=direct")
//        webview.await()


        // take screenshot
        Log.d("main", "Load page finished")
        val bitmap = takeScreenshot()

        Log.d("main", "Successful")

        // Remove webview from scrollview
        scrollView.removeView(webview)

        // Put webview to container and change webview height to match parent
        container.addView(
            webview,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // Remove scrollview from container
        container.removeView(scrollView)
//        webview.loadDataWithBaseURL(null, testHtml, "text/html", "UTF-8", null)
//        webview.loadData(testHtml, "text/html", "UTF-8")
        return bitmap
    }

    private fun takeScreenshot(): Bitmap {
//        val bitmap = Bitmap.createBitmap(webview.width, webview.height, Bitmap.Config.ARGB_8888)
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        webview.draw(canvas)

        return bitmap
    }

    private fun saveImage(bitmap: Bitmap) {
        val filename = "testscreenshot.png"
        val sd = Environment.getRootDirectory()
        val dest = File(filesDir, filename)
        try {
            val out = FileOutputStream(dest)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun WebView.await() {
    this.settings.javaScriptEnabled = true
    val webViewClient = TestWebViewClient()
    this.webViewClient = webViewClient
    webViewClient.await()
    this.loadUrl("https://dashboard.omise.co/signup?locale=en&origin=direct")
}

class TestWebViewClient : WebViewClient() {
    private val isFinished = AtomicBoolean(false)

    init {
        Log.d("webview client", "init()")
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return false
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        Log.d("webview client", "onPageStarted()")
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)
        isFinished.set(true)
        Log.d("webview client", "onPageCommitVisible()")
    }

    fun await() {
        runBlocking {
            while (true) {
                Log.d(
                    "webview client",
                    "waiting on Pid: ${Process.myPid()}, main's Pid: ${Thread.currentThread().name}, isFinished: $isFinished"
                )
                delay(1000)
                if (isFinished.compareAndSet(true, false)) {
                    break
                }
            }
        }
    }
}