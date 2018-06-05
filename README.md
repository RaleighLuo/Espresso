# Espresso
Excel驱动自动创建Espresso单元测试类

博客地址：http://blog.csdn.net/xiaoluoli88/article/details/79635119

将持续更新，不断完善～

简介：不想写单元测试的程序员们，可使用此工具类，快速生成单元测试类，并自动执行，帮助程序员提高代码质量～

使用方法：请参考项目下［使用说明文档.docx］
或博客：http://blog.csdn.net/xiaoluoli88/article/details/79635992

IReadSheets API

1.setTransformLanguage 设置测试类转化语言 支持java／kotlin 

2.read 读取excel并创建所有单元测试用例 

3.executeAllTest 集成所有单元测试用例，并自动执行（执行前提，设备必须先安装app）

View API

清空并输入文本 input_text

追加输入文本 input_append_text

点击指定id控件 click_id

点击指定文字控件 click_text

点击指定背景控件 click_background

验证控件提示文字 check_id_hint_text

验证控件文字是否包含指定文字 check_id_contains_text

验证控件文本 check_id_text

验证控件是否可见 check_id_displayed

验证指定文本是否显示 check_text_displayed

验证包含文字是否显示 check_contains_text_displayed

验证指定文本不存在 check_text_not_exist

验证控件不存在 check_id_not_exist

验证控件是否被选中 check_id_checked

验证控件是否可用 check_id_enabled

验证控件是否可点击 check_id_clickable

验证控件是否可长按 check_id_longclickable

验证EditTextView控件输入的类型 check_input_type

下滑 swipe_down

上滑 swipe_up

左滑 swipe_left

右滑 swipe_right

检查Spinner是否选中了指定文本 spinner_check_text

Spinner选择了指定文本 spinner_select_text

选择picker日期 picker_set_date

选中picker时间 picker_set_time

System

点击系统返回键 press_back 
点击系统菜单键 press_menu_key 
点击软键盘action键 press_ime_action_key 
点击指定键 press_key 
点击菜单项 navigate_to_menu_id 
验证Activity是否Finished check_finished 
验证Activity是否Destroyed check_destroyed 
验证Activity是否横竖屏切换 check_changing_configurations 
截屏 take_screenshot

Intent

拦截指定classname的Intent intercept_classname 

拦截指定classname的Intent并随机一张本地图片地址 
intercept_classname_extra_image 

拦截指定classname的Intent并extras bundle返回随机一张本地图片地址 
intercept_classname_extras_image 

拦截指定classname的Intent并随机返回extra指定数量的本地图片地址 
intercept_classname_extra_images 

拦截指定classname的Intent 并随机返回extras bundle指定数量的本地图片地址 intercept_classname_extras_images 

验证指定classname的Intent verify_classname 

验证指定classname的Intent 并返回了指定键值对(字符串需引号) 
verify_classname_extra 

验证指定classname的Intent并返回了bundle指定键值对(字符串需引号) 
verify_classname_extras 

拦截指定Action的Intent(action字符串需双引号) intercept_action 

验证指定Action的Intent(action字符串需双引号) verify_action 

拦截指定Action和package的Intent verify_action_topackage 

验证不包含指定的Intent verify_filter_classname 

拦截指定包名 intercept_packagename 

验证指定包名 verify_packagename

Window

验证Dialog中是否有指定文字 dialog_check_text

验证Dialog中是否有包含文字 dialog_check_contains_text

点击Popwindow中的控件 popwindow_click_item

点击Alertwindow中的控件 alertwindow_click_text

验证Toast toast_check_text

验证Toast 不是指定文字 toast_check_filter_text

根据文本，多窗口切换 change_window_by_text

RecyclerView

上拉 pull_from_start

下拉 pull_from_end

滑动到底部 scroll_to_end

验证列表的项数量为指定数量 check_item_counts

验证列表至少有一项 check_has_child

点击列表中指定位置项 click_item

长按列表中指定位置项 longclick_item

点击指定项控件 click_item_view_id

数据源是map 点击指定KeyMap项 click_item_map

滑动到指定位置项 scroll_to_position

滑动到指定文字项 scroll_to_text

点击指定文字项 click_item_text

验证指定项控件文字 check_item_view_text

ViewPager

向左滑动 scroll_left

向右滑动 scroll_right

滑动到第一项 scroll_to_first

滑动到最后一项 scroll_to_last

滑动到指定项 scroll_to_page

点击两项 click_between_two_titles

ListView

验证指定键值对已经显示在列表中 check_item_displayed

点击指定项 click_item

点击指定项并其开始文字为指定文字 click_item_start_text

点击指定项控件 click_item_view

Drawer

打开抽屉 open_closed_drawer

打开抽屉 close_opened_drawer

关闭抽屉 open_closed_drawer_gravity

关闭抽屉 close_opened_drawer_gravity

WebView
强制开启javascript属性 forceJavascriptEnabled
点击 click
点击子级 click_sub
清除文本 clear_text
清除子级文本 clear_text_sub
输入文本  input_text
输入文本  input_text_sub
验证文本  check_text
验证子级文本 check_text_sub
验证Url check_url
验证子级Url check_url_sub
