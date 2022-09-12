package com.akshai.azymptotest.repo.entity

import com.google.gson.annotations.SerializedName

data class MockApiEntity(

	@field:SerializedName("__appConfig")
	val appConfig: AppConfig? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem>? = null
)

data class Footer(

	@field:SerializedName("textColour")
	val textColour: String? = null,

	@field:SerializedName("bgColour")
	val bgColour: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class Grid(

	@field:SerializedName("md")
	val md: Int? = null,

	@field:SerializedName("sm")
	val sm: Int? = null,

	@field:SerializedName("xs")
	val xs: Int? = null,

	@field:SerializedName("lg")
	val lg: Int? = null
)

data class Params(
	val any: Any? = null
)

data class Header(

	@field:SerializedName("onScrollBgColour")
	val onScrollBgColour: String? = null,

	@field:SerializedName("textColour")
	val textColour: String? = null,

	@field:SerializedName("fgStyle")
	val fgStyle: String? = null,

	@field:SerializedName("bgColour")
	val bgColour: String? = null
)

data class AppConfig(

	@field:SerializedName("BRANDCOLOURS")
	val bRANDCOLOURS: List<String?>? = null,

	@field:SerializedName("WEBAPP_SSR_LINK")
	val wEBAPPSSRLINK: String? = null,

	@field:SerializedName("styleConfig")
	val styleConfig: StyleConfig? = null,

	@field:SerializedName("PATHS")
	val pATHS: PATHS? = null,

	@field:SerializedName("LOGO")
	val lOGO: String? = null,

	@field:SerializedName("ECOMPROVIDER")
	val eCOMPROVIDER: String? = null,

	@field:SerializedName("TINT_COLOUR")
	val tINTCOLOUR: String? = null,

	@field:SerializedName("DEFAULT_CURRENCY_SYMBOL")
	val dEFAULTCURRENCYSYMBOL: String? = null,

	@field:SerializedName("NAME")
	val nAME: String? = null,

	@field:SerializedName("DEFAULT_LANG")
	val dEFAULTLANG: String? = null,

	@field:SerializedName("COMPANY_NAME")
	val cOMPANYNAME: String? = null,

	@field:SerializedName("features")
	val features: List<FeaturesItem?>? = null,

	@field:SerializedName("DEFAULT_CURRENCY")
	val dEFAULTCURRENCY: String? = null,

	@field:SerializedName("DEFAULT_ZONEINFO")
	val dEFAULTZONEINFO: String? = null,

	@field:SerializedName("COGNITO_USER_POOL")
	val cOGNITOUSERPOOL: String? = null,

	@field:SerializedName("PASSWORD_POLICY")
	val pASSWORDPOLICY: String? = null,

	@field:SerializedName("GENERICSITE")
	val gENERICSITE: Boolean? = null,

	@field:SerializedName("GFONTNAME")
	val gFONTNAME: String? = null,

	@field:SerializedName("TENANT_ID")
	val tENANTID: String? = null,

	@field:SerializedName("DEFAULT_COUNTRY")
	val dEFAULTCOUNTRY: String? = null,

	@field:SerializedName("TITLE")
	val tITLE: String? = null,

	@field:SerializedName("COGNITO_USER_POOL_CLIENT_ID")
	val cOGNITOUSERPOOLCLIENTID: String? = null,

	@field:SerializedName("CLOUD_REGION")
	val cLOUDREGION: String? = null,

	@field:SerializedName("USER_PRIMARY")
	val uSERPRIMARY: String? = null
)

data class Badge(

	@field:SerializedName("catTitleColour")
	val catTitleColour: String? = null,

	@field:SerializedName("prodBg")
	val prodBg: String? = null,

	@field:SerializedName("prodTitleColour")
	val prodTitleColour: String? = null,

	@field:SerializedName("catBgHover")
	val catBgHover: String? = null,

	@field:SerializedName("catBg")
	val catBg: String? = null,

	@field:SerializedName("prodBgHover")
	val prodBgHover: String? = null
)

data class Faqs(

	@field:SerializedName("type")
	val type: String? = null
)

data class FeaturesItem(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("iType")
	val iType: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class Testimonials(

	@field:SerializedName("minHeight")
	val minHeight: Int? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class PATHS(

	@field:SerializedName("CONTACT")
	val cONTACT: String? = null,

	@field:SerializedName("PRIVACY")
	val pRIVACY: String? = null,

	@field:SerializedName("SEARCH")
	val sEARCH: String? = null,

	@field:SerializedName("TERMS")
	val tERMS: String? = null,

	@field:SerializedName("BRAND")
	val bRAND: String? = null,

	@field:SerializedName("ORDER")
	val oRDER: String? = null,

	@field:SerializedName("PROFILE")
	val pROFILE: String? = null,

	@field:SerializedName("CATEGORY")
	val cATEGORY: String? = null,

	@field:SerializedName("PRODUCT")
	val pRODUCT: String? = null,

	@field:SerializedName("FAQ")
	val fAQ: String? = null,

	@field:SerializedName("BLOG")
	val bLOG: String? = null,

	@field:SerializedName("LIST")
	val lIST: String? = null,

	@field:SerializedName("ABOUT")
	val aBOUT: String? = null,

	@field:SerializedName("CART")
	val cART: String? = null
)

data class ItemsItem(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("params")
	val params: Params? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("parentId")
    var parentId: String? = null,

	@field:SerializedName("textColourTheme")
	val textColourTheme: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("imageName")
	val imageName: String? = null
)

data class Prod(

	@field:SerializedName("imageAspectRatio")
	val imageAspectRatio: Int? = null,

	@field:SerializedName("textColour")
	val textColour: String? = null,

	@field:SerializedName("imageBgColour")
	val imageBgColour: String? = null,

	@field:SerializedName("outlineColour")
	val outlineColour: Any? = null,

	@field:SerializedName("catTitle")
	val catTitle: String? = null,

	@field:SerializedName("bgColour")
	val bgColour: String? = null,

	@field:SerializedName("imageMode")
	val imageMode: String? = null,

	@field:SerializedName("alignment")
	val alignment: String? = null,

	@field:SerializedName("imageOutlineColour")
	val imageOutlineColour: String? = null
)

data class Global(

	@field:SerializedName("cornerType")
	val cornerType: String? = null,

	@field:SerializedName("fontSizeType")
	val fontSizeType: String? = null,

	@field:SerializedName("textColour")
	val textColour: String? = null,

	@field:SerializedName("tab")
	val tab: Boolean? = null,

	@field:SerializedName("tintColour")
	val tintColour: String? = null,

	@field:SerializedName("tintContrastColour")
	val tintContrastColour: String? = null,

	@field:SerializedName("bgColour")
	val bgColour: String? = null
)

data class StyleConfig(

	@field:SerializedName("benefits")
	val benefits: Benefits? = null,

	@field:SerializedName("prod")
	val prod: Prod? = null,

	@field:SerializedName("footer")
	val footer: Footer? = null,

	@field:SerializedName("subscribe")
	val subscribe: Subscribe? = null,

	@field:SerializedName("banner")
	val banner: Banner? = null,

	@field:SerializedName("global")
	val global: Global? = null,

	@field:SerializedName("blog")
	val blog: Blog? = null,

	@field:SerializedName("testimonials")
	val testimonials: Testimonials? = null,

	@field:SerializedName("badge")
	val badge: Badge? = null,

	@field:SerializedName("faqs")
	val faqs: Faqs? = null,

	@field:SerializedName("cat")
	val cat: Cat? = null,

	@field:SerializedName("header")
	val header: Header? = null,

	@field:SerializedName("misc")
	val misc: Misc? = null
)

data class Benefits(

	@field:SerializedName("type")
	val type: String? = null
)

data class Subscribe(

	@field:SerializedName("type")
	val type: Any? = null
)

data class Blog(

	@field:SerializedName("imageAspectRatio")
	val imageAspectRatio: Int? = null,

	@field:SerializedName("textColour")
	val textColour: String? = null,

	@field:SerializedName("imageBgColour")
	val imageBgColour: Any? = null,

	@field:SerializedName("outlineColour")
	val outlineColour: Any? = null,

	@field:SerializedName("tagTitle")
	val tagTitle: String? = null,

	@field:SerializedName("bgColour")
	val bgColour: Any? = null,

	@field:SerializedName("imageMode")
	val imageMode: String? = null,

	@field:SerializedName("alignment")
	val alignment: String? = null,

	@field:SerializedName("imageOutlineColour")
	val imageOutlineColour: Any? = null
)

data class Banner(

	@field:SerializedName("sizeType")
	val sizeType: String? = null,

	@field:SerializedName("textColour")
	val textColour: String? = null,

	@field:SerializedName("heightMobileApp")
	val heightMobileApp: Int? = null,

	@field:SerializedName("subTextColour")
	val subTextColour: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class Cat(

	@field:SerializedName("hide")
	val hide: Boolean? = null,

	@field:SerializedName("imageAspectRatio")
	val imageAspectRatio: Double? = null,

	@field:SerializedName("textColour")
	val textColour: String? = null,

	@field:SerializedName("imageBgColour")
	val imageBgColour: String? = null,

	@field:SerializedName("outlineColour")
	val outlineColour: Any? = null,

	@field:SerializedName("grid")
	val grid: Grid? = null,

	@field:SerializedName("bgColour")
	val bgColour: String? = null,

	@field:SerializedName("imageMode")
	val imageMode: String? = null,

	@field:SerializedName("title")
	val title: Any? = null,

	@field:SerializedName("alignment")
	val alignment: String? = null,

	@field:SerializedName("imageOutlineColour")
	val imageOutlineColour: Any? = null,

	@field:SerializedName("titleOverlay")
	val titleOverlay: Boolean? = null
)

data class Misc(

	@field:SerializedName("viewMoreBtn")
	val viewMoreBtn: String? = null,

	@field:SerializedName("tilelist")
	val tilelist: String? = null
)
