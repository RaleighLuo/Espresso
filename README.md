# Espresso
Excel驱动自动创建Espresso单元测试类

简介：不想写单元测试的程序员们，可使用此工具类，快速生成单元测试类，并自动执行，帮助程序员提高代码质量～

使用方法：请参考项目下［使用说明文档.docx］

IReadSheets API

1.setTransformLanguage  设置测试类转化语言 支持java／kotlin

2.read 读取excel并创建所有单元测试用例

3.executeAllTest 集成所有单元测试用例，并自动执行（执行前提，设备必须先安装app）


View

清空并输入文本	Viewinput_text

追加输入文本	Viewinput_append_text

点击指定id控件	Viewclick_id

点击指定文字控件	Viewclick_text

点击指定背景控件	Viewclick_background

验证控件提示文字	Viewcheck_id_hint_text

验证控件文字是否包含指定文字	Viewcheck_id_contains_text

验证控件文本	Viewcheck_id_text

验证控件是否可见	Viewcheck_id_displayed

验证指定文本是否显示	Viewcheck_text_displayed

验证包含文字是否显示	Viewcheck_contains_text_displayed

验证指定文本不存在	Viewcheck_text_not_exist

验证控件不存在	Viewcheck_id_not_exist

验证控件是否被选中	Viewcheck_id_checked

验证控件是否可用	Viewcheck_id_enabled

验证控件是否可点击	Viewcheck_id_clickable

验证控件是否可长按	Viewcheck_id_longclickable

验证EditTextView控件输入的类型	Viewcheck_input_type

下滑	Viewswipe_down

上滑	Viewswipe_up

左滑	Viewswipe_left

右滑	Viewswipe_right

检查Spinner是否选中了指定文本	Viewspinner_check_text

Spinner选择了指定文本	Viewspinner_select_text

选择picker日期	Viewpicker_set_date

选中picker时间	Viewpicker_set_time

System

点击系统返回键	Systempress_back

点击系统菜单键	Systempress_menu_key

点击软键盘action键	Systempress_ime_action_key

点击指定键	Systempress_key

点击菜单项	Systemnavigate_to_menu_id

验证Activity是否Finished	Systemcheck_finished

验证Activity是否Destroyed	Systemcheck_destroyed

验证Activity是否横竖屏切换	Systemcheck_changing_configurations

截屏	Systemtake_screenshot
	
Intent

拦截指定classname的Intent	Intentintercept_classname

拦截指定classname的Intent 并随机一张本地图片地址	Intentintercept_classname_extra_image

拦截指定classname的Intent 并extras bundle返回随机一张本地图片地址	Intentintercept_classname_extras_image

拦截指定classname的Intent 并随机返回extra指定数量的本地图片地址	Intentintercept_classname_extra_images

拦截指定classname的Intent 并随机返回extras bundle指定数量的本地图片地址	Intentintercept_classname_extras_images

 验证指定classname的Intent	Intentverify_classname
 
验证指定classname的Intent 并返回了指定键值对(字符串需引号)	Intentverify_classname_extra

验证指定classname的Intent 并返回了bundle指定键值对(字符串需引号)	Intentverify_classname_extras

拦截指定Action的Intent(action字符串需双引号)	Intentintercept_action

验证指定Action的Intent(action字符串需双引号)	Intentverify_action

拦截指定Action和package的Intent	Intentverify_action_topackage

验证不包含指定的Intent	Intentverify_filter_classname

拦截指定包名	Intentintercept_packagename

验证指定包名	Intentverify_packagename
	
Window

验证Dialog中是否有指定文字	Windowdialog_check_text

验证Dialog中是否有包含文字	Windowdialog_check_contains_text

点击Popwindow中的控件	Windowpopwindow_click_item

点击Alertwindow中的控件	Windowalertwindow_click_text

验证Toast	Windowtoast_check_text

验证Toast 不是指定文字	Windowtoast_check_filter_text

根据文本，多窗口切换	Windowchange_window_by_text
	
RecyclerView

上拉	RecyclerViewpull_from_start

下拉	RecyclerViewpull_from_end

滑动到底部	RecyclerViewscroll_to_end

验证列表的项数量为指定数量	RecyclerViewcheck_item_counts

验证列表至少有一项	RecyclerViewcheck_has_child

点击列表中指定位置项	RecyclerViewclick_item

长按列表中指定位置项	RecyclerViewlongclick_item

点击指定项控件	RecyclerViewclick_item_view_id

数据源是map 点击指定KeyMap项	RecyclerViewclick_item_map

滑动到指定位置项	RecyclerViewscroll_to_position

滑动到指定文字项	RecyclerViewscroll_to_text

点击指定文字项	RecyclerViewclick_item_text

验证指定项控件文字	RecyclerViewcheck_item_view_text
	
ViewPager

向左滑动	ViewPagerscroll_left

向右滑动	ViewPagerscroll_right

滑动到第一项	ViewPagerscroll_to_first

滑动到最后一项	ViewPagerscroll_to_last

滑动到指定项	ViewPagerscroll_to_page

点击两项	ViewPagerclick_between_two_titles
	
ListView

验证指定键值对已经显示在列表中	ListViewcheck_item_displayed

点击指定项	ListViewclick_item

点击指定项并其开始文字为指定文字	ListViewclick_item_start_text

点击指定项控件	ListViewclick_item_view
	
Drawer

打开抽屉	Draweropen_closed_drawer

打开抽屉	Drawerclose_opened_drawer

关闭抽屉	Draweropen_closed_drawer_gravity

关闭抽屉	Drawerclose_opened_drawer_gravity
 
