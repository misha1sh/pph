package game.views.dialogs

import com.github.servb.pph.config.Data
import com.github.servb.pph.config.DifficultyLevel
import com.github.servb.pph.network.LocalNetClient
import com.github.servb.pph.network.LocalNetManager
import com.github.servb.pph.network.LocalNetServer
import com.github.servb.pph.pheroes.common.GfxId
import com.github.servb.pph.pheroes.common.TextResId
import game.logic.players.CastleType
import game.logic.players.PlayerId
import game.logic.players.PlayerType
import game.logic.players.PlayerTypeMask
import com.github.servb.pph.pheroes.game.*
import com.github.servb.pph.util.asPoint
import com.github.servb.pph.util.helpertype.and
import com.github.servb.pph.util.helpertype.getByValue
import com.github.servb.pph.util.helpertype.or
import com.soywiz.korma.geom.*
import game.logic.mapInfo.NetGameType
import game.logic.mapInfo.iMapInfo
import game.views.control.iPHLabel
import game.views.control.iTextButton
import gxlib.app.iViewMgr
import gxlib.graphics.dib.cColor
import gxlib.graphics.text.iTextComposer
import gxlib.utils.Alignment
import gxlib.views.*

enum class PlayerState {
    EMPTY,
    WAITING,
    CONNECTED,
    YOU
}

private class iPlayerBtn : iButton {

    private val m_pid: PlayerId
    private var m_pt: PlayerType
    private var m_playerState: PlayerState

    constructor(
            pViewMgr: iViewMgr,
            pCmdHandler: IViewCmdHandler,
            rect: IRectangleInt,
            pid: PlayerId,
            pt: PlayerType,
            ps: PlayerState,
            uid: UInt,
            state: Int = ViewState.Visible or ViewState.Enabled
    ) : super(pViewMgr, pCmdHandler, rect, uid, state) {
        m_pid = pid
        m_pt = pt
        m_playerState = ps
    }

    fun SetPlayerState(ps: PlayerState) {
        m_playerState = ps
    }

    override fun OnCompose() {
        val fullRc = GetScrRect()

        val rc = fullRc.setSize(fullRc.width, fullRc.width)

        gApp.Surface().FrameRect(rc, cColor.Black.pixel)
        rc.rect.inflate(-1)
        gApp.Surface().FillRect(rc, m_pid.color, 96u)
        ButtonFrame(gApp.Surface(), rc, m_state)

        val sid = if (m_pt == PlayerType.HUMAN) {
            GfxId.PDGG_ICN_PLT_HUMAN
        } else {
            GfxId.PDGG_ICN_PLT_AI
        }.v
        val op = IPointInt(
            rc.x + (rc.width / 2) - (gGfxMgr.Dimension(sid).width / 2),
            rc.y + (rc.height / 2) - (gGfxMgr.Dimension(sid).height / 2)
        )
        gGfxMgr.Blit(sid, gApp.Surface(), op)

        val fc = iTextComposer.FontConfig(dlgfc_splain)

        if (m_playerState != PlayerState.EMPTY) {
            gTextComposer.TextOut(
                fc,
                gApp.Surface(),
                fullRc.asPoint(),
                when(m_playerState) {
                    PlayerState.WAITING -> "Waiting"
                    PlayerState.CONNECTED -> "Ready"
                    PlayerState.YOU -> "You"
                    else -> throw IllegalArgumentException("$m_playerState")
                },
                fullRc,
                Alignment.AlignBottom,
                PointInt(0, 12)
            )
        }


        /*  // commented in sources
		if (m_pt == PT_HUMAN) gTextComposer.TextOut(dlgfc_stopic, gApp.Surface(), iPoint(), _T("Human"), rc, AlignCenter);
		else if (m_pt == PT_COMPUTER) gTextComposer.TextOut(dlgfc_splain, gApp.Surface(), iPoint(), _T("CPU"), rc, AlignCenter);
		*/

        if (!IsEnabled()) {
            gApp.Surface().FillRect(rc, cColor.Gray64.pixel, 128u)
        }
    }

    fun PlayerType(): PlayerType = m_pt

    fun SetPlayerType(pt: PlayerType){
        m_pt = pt
    }

    fun TogglePlayerType(): PlayerType {
        if (m_pt == PlayerType.HUMAN) {
            m_pt = PlayerType.COMPUTER
        } else {
            m_pt = PlayerType.HUMAN
        }
        return m_pt
    }
}



private class iNetGameTypeBtn : iButton {

    private var m_gameType: NetGameType

    constructor(
            pViewMgr: iViewMgr,
            pCmdHandler: IViewCmdHandler,
            rect: IRectangleInt,
            gameType: NetGameType,
            uid: UInt,
            state: Int = ViewState.Visible or ViewState.Enabled
    ) : super(pViewMgr, pCmdHandler, rect, uid, state) {
        m_gameType = gameType
    }

    override fun OnCompose() {
        val rc = GetScrRect()
        gApp.Surface().FrameRect(rc, cColor.Black.pixel)
        rc.rect.inflate(-1)
        gApp.Surface().FillRect(rc,
            when(m_gameType) {
                NetGameType.Local -> cColor.Red.pixel
                NetGameType.Client -> cColor.Green.pixel
                 NetGameType.Server -> cColor.Blue.pixel
             }, 96u)
        ButtonFrame(gApp.Surface(), rc, m_state)

        val fc = iTextComposer.FontConfig(dlgfc_plain)
        gTextComposer.TextOut(
            fc,
            gApp.Surface(),
            rc.asPoint(),
            when(m_gameType) {
                NetGameType.Local -> "Local"
                NetGameType.Client -> "Client"
                NetGameType.Server -> "Server"
            },
            rc,
            Alignment.AlignCenter,
            if ((m_state and iButton.State.Pressed.v) != 0) PointInt(1, 1) else PointInt()
        )

//        val sid = if (m_pt == PlayerType.HUMAN) {
//            GfxId.PDGG_ICN_PLT_HUMAN
//        } else {
//            GfxId.PDGG_ICN_PLT_AI
//        }.v
//        val op = IPointInt(
//            rc.x + (rc.width / 2) - (gGfxMgr.Dimension(sid).width / 2),
//            rc.y + (rc.height / 2) - (gGfxMgr.Dimension(sid).height / 2)
//        )
//        gGfxMgr.Blit(sid, gApp.Surface(), op)
        /*  // commented in sources
		if (m_pt == PT_HUMAN) gTextComposer.TextOut(dlgfc_stopic, gApp.Surface(), iPoint(), _T("Human"), rc, AlignCenter);
		else if (m_pt == PT_COMPUTER) gTextComposer.TextOut(dlgfc_splain, gApp.Surface(), iPoint(), _T("CPU"), rc, AlignCenter);
		*/

        if (!IsEnabled()) {
            gApp.Surface().FillRect(rc, cColor.Gray64.pixel, 128u)
        }
    }

    fun GetGameType(): NetGameType = m_gameType

    fun ToggleGameType(): NetGameType {
        if (m_gameType == NetGameType.Local) {
            m_gameType = NetGameType.Client
        } else if (m_gameType == NetGameType.Client) {
            m_gameType = NetGameType.Server
        } else {
            m_gameType = NetGameType.Local
        }
        return m_gameType
    }
}

private class iNationBtn : iButton, IViewCmdHandler {

    private val m_bFixed: Boolean
    private val m_pid: PlayerId
    private var m_nt: CastleType

    constructor(
            pViewMgr: iViewMgr,
            rect: IRectangleInt,
            pid: PlayerId,
            nt: CastleType,
            uid: UInt,
            bFixed: Boolean
    ) : super(
        pViewMgr,
        null,
        rect,
        uid,
        if (bFixed) ViewState.Visible.v else (ViewState.Visible or ViewState.Enabled)
    ) {
        m_pCmdHandler = this
        m_pid = pid
        m_nt = nt
        m_bFixed = bFixed
    }

    fun PlayerNation(): CastleType = m_nt

    override fun OnCompose() {
        val rc = GetScrRect()
        gApp.Surface().FrameRect(rc, cColor.Black.pixel)
        rc.rect.inflate(-1)

        val icn = GfxId.PDGG_CTL_SICONS(CastleType.COUNT.v * 2 + m_nt.v)
        //PDGG_CTL_SICONS + (NATION_COUNT-NATION_HIGHMEN)*2 + (m_nt-NATION_HIGHMEN)  // commented in sources
        BlitIcon(gApp.Surface(), icn, rc)
        ButtonFrame(gApp.Surface(), rc, m_state)

        if (!IsEnabled()) {
            gApp.Surface().FillRect(rc, cColor.Gray64.pixel, 128u)
        }
    }

    override suspend fun iCMDH_ControlCommand(pView: iView, cmd: CTRL_CMD_ID, param: Int) {
        check(!m_bFixed)
        if (cmd in setOf(CTRL_CMD_ID.BUTTON_CLICK, CTRL_CMD_ID.BUTTON_DOUBLE_CLICK)) {
            m_nt = when (m_nt) {
                CastleType.RANDOM -> CastleType.CITADEL
                else -> getByValue(m_nt.v + 1)
            }
        }
    }
}

private class iDifLvlTab : iTabbedSwitch {

    constructor(pViewMgr: iViewMgr, pCmdHandler: IViewCmdHandler, rect: IRectangleInt, uid: UInt, state: Int) : super(
        pViewMgr,
        pCmdHandler,
        rect,
        Data.difficultyLevels!!.count(),
        uid,
        state
    )

    override fun ComposeTabItem(idx: Int, itemState: Int, rect: IRectangleInt) {
        val orc = RectangleInt(rect)
        orc.rect.inflate(-1)
        val icn = GfxId.PDGG_ICN_DIF_LEVEL(idx)
        BlitIcon(gApp.Surface(), icn, orc)
        if ((itemState and iButton.State.Selected) != 0) {
            gApp.Surface().FrameRect(orc, cColor.Red.pixel)
        }
        if (!IsEnabled()) {
            gApp.Surface().FillRect(orc, cColor.Gray64.pixel, 128u)
        }
    }
}

class iScenPropsDlg : iBaseGameDlg {

    private lateinit var m_pDfcLabel: iPHLabel
    private lateinit var m_pOkBtn: iTextButton

    private val m_btnPlayers: MutableList<iPlayerBtn> = mutableListOf()
    private val m_btnNations: MutableList<iNationBtn> = mutableListOf()

    private var m_server: LocalNetServer? = null
    private var m_client: LocalNetClient? = null


    private lateinit var m_difLevel: iDifLvlTab
    private lateinit var m_btnNetType: iNetGameTypeBtn

    private val m_scProps: iMapInfo
    private val m_bReadOnly: Boolean

    constructor(pViewMgr: iViewMgr, scProps: iMapInfo, bReadOnly: Boolean) : super(pViewMgr, PlayerId.NEUTRAL) {
        m_scProps = scProps
        m_bReadOnly = bReadOnly
    }

    fun ScenProps(): iMapInfo = m_scProps


    override suspend fun OnCreateDlg() {
        val clRect = ClientRect()

        var yp = clRect.y
        AddChild(
            iPHLabel(
                m_pMgr,
                IRectangleInt(clRect.x, yp, clRect.width, 15),
                m_scProps.m_Name,
                Alignment.AlignTop,
                dlgfc_hdr
            )
        )
        yp += 20

        m_pDfcLabel = iPHLabel(
            m_pMgr,
            IRectangleInt(clRect.x, yp, clRect.width, 15),
            GetDfcString(m_scProps.m_Difficulty),
            Alignment.AlignTop,
            dlgfc_topic
        )
        AddChild(m_pDfcLabel)
        yp += 15

        m_difLevel = iDifLvlTab(
            m_pMgr,
            this,
            IRectangleInt(clRect.x + (clRect.width / 2 - 95), yp, 190, 38),
            301u,
            if (m_bReadOnly) ViewState.Visible.v else (ViewState.Visible or ViewState.Enabled)
        )
        AddChild(m_difLevel)
        if (m_scProps.m_Difficulty == Data.difficultyLevels!!.UNDEFINED) {
            m_scProps.m_Difficulty = Data.difficultyLevels!!.NORMAL
        }
        m_difLevel.SetCurrentTab(m_scProps.m_Difficulty.v)
        m_pDfcLabel.SetText(GetDfcString(m_scProps.m_Difficulty))
        yp += 45

        val cnt = m_scProps.TotalPlayers()
        val btnsw = cnt * 34 + (cnt - 1) * 3
        val sy = yp
        var sx = clRect.x + (clRect.width / 2 - btnsw / 2)
        repeat(cnt) { xx ->
            val pNatBtn = iNationBtn(
                m_pMgr,
                IRectangleInt(sx, sy, 34, 22),
                m_scProps.m_Players[xx].m_Id,
                m_scProps.m_Players[xx].m_Nation,
                (150 + xx).toUInt(),
                m_scProps.m_Players[xx].m_Nation != CastleType.RANDOM
            )
            AddChild(pNatBtn)
            m_btnNations.add(pNatBtn)
            val tp = when (m_scProps.m_Players[xx].m_TypeMask) {
                PlayerTypeMask.HUMAN_ONLY -> PlayerType.HUMAN
                PlayerTypeMask.COMPUTER_ONLY -> PlayerType.COMPUTER
                else -> when (xx) {
                    0 -> PlayerType.HUMAN
                    else -> PlayerType.COMPUTER
                }
            }
            val pPlBtn = iPlayerBtn(
                m_pMgr,
                this,
                IRectangleInt(sx + 2, sy + 25, 30, 30 + 10),
                m_scProps.m_Players[xx].m_Id,
                if (m_bReadOnly) m_scProps.m_Players[xx].m_Type else tp,
                PlayerState.EMPTY,
                (200 + xx).toUInt(),
                if (!m_bReadOnly && (m_scProps.m_Players[xx].m_TypeMask == PlayerTypeMask.HUMAN_OR_COMPUTER)) (ViewState.Visible or ViewState.Enabled) else ViewState.Visible.v
            )
            AddChild(pPlBtn)
            m_btnPlayers.add(pPlBtn)

            sx += 37
        }

        m_btnNetType = iNetGameTypeBtn(
            m_pMgr,
            this,
            IRectangleInt(sx, sy + 25, 34, 34),
            m_scProps.m_netGameType,
            337.toUInt(), // uid
            (ViewState.Visible or ViewState.Enabled)
        )
        AddChild(m_btnNetType)

        // Buttons
        val npos = clRect.x + (clRect.width / 2 - 80)
        m_pOkBtn = iTextButton(
            m_pMgr,
            this,
            IRectangleInt(npos, clRect.y2 - DEF_BTN_HEIGHT, 50, DEF_BTN_HEIGHT),
            TextResId.TRID_OK,
            DLG_RETCODE.OK.v.toUInt(),
            ViewState.Visible.v
        )
        AddChild(m_pOkBtn)
        AddChild(
            iTextButton(
                m_pMgr,
                this,
                IRectangleInt(npos + 55, clRect.y2 - DEF_BTN_HEIGHT, 50, DEF_BTN_HEIGHT),
                TextResId.TRID_INFO,
                401u
            )
        )
        AddChild(
            iTextButton(
                m_pMgr,
                this,
                IRectangleInt(npos + 110, clRect.y2 - DEF_BTN_HEIGHT, 50, DEF_BTN_HEIGHT),
                TextResId.TRID_CANCEL,
                DLG_RETCODE.CANCEL.v.toUInt()
            )
        )

        UpdateControls()
    }

    override fun DoCompose(clRect: IRectangleInt) {
        UpdateConnectionState()
        // empty in sources
    }

    override fun ClientSize(): SizeInt = SizeInt(270, 150 + DEF_BTN_HEIGHT)

    private fun UpdateConnectionState() {
        val humanCount = m_scProps.m_Players.indices.count { m_btnPlayers[it].PlayerType() == PlayerType.HUMAN }

        when (m_btnNetType.GetGameType()) {
            NetGameType.Local -> {
                m_pOkBtn.SetEnabled(humanCount != 0)
            }
            NetGameType.Client -> {
                val client_state = when (m_client?.connected()) {
                    true -> PlayerState.CONNECTED
                    false, null -> PlayerState.WAITING
                }
                m_btnPlayers[0].SetPlayerState(client_state)
                m_pOkBtn.SetEnabled(false)

                if (m_client?.started() == true) {
                    FinishDialog(DLG_RETCODE.OK.v)
                }
            }
            NetGameType.Server -> {
                val client_state = when (m_server?.hasClient()) {
                    true -> PlayerState.CONNECTED
                    false, null -> PlayerState.WAITING
                }
                m_btnPlayers[1].SetPlayerState(
                    client_state
                )
                m_pOkBtn.SetEnabled(client_state == PlayerState.CONNECTED)
            }
        }
    }

    private fun UpdateControls() {
        when (m_btnNetType.GetGameType()) {
            NetGameType.Local -> {
                for (btnPlayer in m_btnPlayers) {
                    btnPlayer.SetPlayerState(PlayerState.EMPTY)
                }
                m_server?.close()
                m_server = null
                m_client?.close()
                m_client = null
            }
            NetGameType.Client -> {
                for (btnPlayer in m_btnPlayers) {
                    btnPlayer.SetPlayerType(PlayerType.COMPUTER)
                }
                m_btnPlayers[0].SetPlayerType(PlayerType.HUMAN)
                m_btnPlayers[0].SetPlayerState(PlayerState.WAITING)
                m_btnPlayers[1].SetPlayerType(PlayerType.HUMAN)
                m_btnPlayers[1].SetPlayerState(PlayerState.YOU)

                m_server?.close()
                m_server = null

                m_client = LocalNetClient("192.168.100.13", 3337)
                m_client!!.run()
            }
            NetGameType.Server -> {
                for (btnPlayer in m_btnPlayers) {
                    btnPlayer.SetPlayerType(PlayerType.COMPUTER)
                }
                m_btnPlayers[0].SetPlayerType(PlayerType.HUMAN)
                m_btnPlayers[0].SetPlayerState(PlayerState.YOU)
                m_btnPlayers[1].SetPlayerType(PlayerType.HUMAN)
                m_btnPlayers[1].SetPlayerState(PlayerState.WAITING)

                m_client?.close()
                m_client = null
                m_server = LocalNetServer(3337)
                m_server!!.run()
            }
        }
    }

    fun FinishDialog(uid: Int) {
        // Setup difficulty
        m_scProps.m_Difficulty = Data.difficultyLevels!![m_difLevel.GetCurrentTab()]!!
        // Setup players
        m_scProps.m_Players.indices.forEach { xx ->
            m_scProps.m_Players[xx].m_Type = m_btnPlayers[xx].PlayerType()
            m_scProps.m_Players[xx].m_Nation = m_btnNations[xx].PlayerNation()
        }
        // Setup net
        m_scProps.m_netGameType = m_btnNetType.GetGameType()
        // if (uid == DLG_RETCODE.OK.v) {
        // m_scProps.netManager = ....convert()
        //}

        when (m_btnNetType.GetGameType()) {
            NetGameType.Local -> {}
            NetGameType.Client -> {
                if (uid == DLG_RETCODE.OK.v) {
                    val v = m_client!!.convert()
                    gGame.setLocalNetManager(LocalNetManager(v, v, m_client!!))
                }
            }
            NetGameType.Server -> {
                if (uid == DLG_RETCODE.OK.v) {
                    val v = m_server!!.convert()
                    gGame.setLocalNetManager(LocalNetManager(v.client, v.server, m_server!!))
                }
            }
        }

        m_server?.close()
        m_client?.close()

        EndDialog(uid)
    }

    override suspend fun iCMDH_ControlCommand(pView: iView, cmd: CTRL_CMD_ID, param: Int) {
        val uid = pView.GetUID().toInt()
        when {
            (uid == DLG_RETCODE.OK.v || uid == DLG_RETCODE.CANCEL.v) && cmd == CTRL_CMD_ID.BUTTON_CLICK -> {
                FinishDialog(uid)
            }
            uid == 301 -> {
                m_pDfcLabel.SetText(GetDfcString(Data.difficultyLevels!![m_difLevel.GetCurrentTab()]!!))
            }
            uid == 401 -> {
                var title = m_scProps.m_Name
                if (m_scProps.m_Version.isNotBlank()) {
                    title += " v.${m_scProps.m_Version}"
                }
                var desc = m_scProps.m_Description
                if (m_scProps.m_Author.isNotBlank()) {
                    desc += "\n\n${gTextMgr[TextResId.TRID_MAP_AUTHOR]}: ${m_scProps.m_Author}"
                }
                val tdlg = iTextDlg(m_pMgr, title, desc, PlayerId.NEUTRAL, dlgfc_topic, dlgfc_splain)
                tdlg.DoModal()
            }
            uid == 337.toInt() -> {
                m_btnNetType.ToggleGameType()
                UpdateControls()
            }
            (uid in 200 until (200 + m_scProps.m_Players.size)) && cmd == CTRL_CMD_ID.BUTTON_CLICK -> {
                val value = uid - 200
                m_btnPlayers[value].TogglePlayerType()
                UpdateControls()
            }
        }
    }

    companion object {

        fun GetDfcString(dl: DifficultyLevel): String =
            "#FCCC${gTextMgr[TextResId.TRID_DIFFICULTY_LEVEL]}: #FFF0${gTextMgr[TextResId.TRID_DIFF_EASY.v + dl.v]}"
    }
}