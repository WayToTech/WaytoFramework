package com.wayto.android.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

/**
 * @Package: com.jinggan.dear.common.dialog
 * @Description:对话框工厂类
 * @author: Aaron
 * @date: 2015-11-28
 * @Time: 16:23
 * @version: V1.0
 */
public class DialogFactory {

    /******************* -------START-------- 碇定和取消Dialog -------START-------- *************************/
    /**
     * 消息提示对话框
     *
     * @param context          上下文
     * @param title            标题
     * @param titleTextColor   标题字体颜色
     * @param titleTextSize    标题字体大小
     * @param titleGravity     标题字体位置
     * @param msg              内容
     * @param msgTextColor     内容字体颜色
     * @param msgTextSize      内容字体大小
     * @param msgGravity       内容字体位置
     * @param confirmBtnStr    确定按钮
     * @param confirmTextColor 确定按钮字体颜色
     * @param confirmTextSize  确定按钮字体大小
     * @param cancelBtnStr     取消按钮
     * @param cancelTextColor  取消按钮字体颜色
     * @param cancelTextSize   取消按钮字体大小
     * @param backCancel       点击返回是否取消对话框[true不取消，false取消]
     * @param confirmListener  确定监听回调
     * @param cancelListener   取消监听回调
     * @param warning          是否只有确定按钮[true有，false没有]
     * @return
     */
    public static Dialog showMsgDialog(Context context, String title,
                                       int titleTextColor, int titleTextSize, int titleGravity,
                                       String msg, int msgTextColor, int msgTextSize, int msgGravity,
                                       String confirmBtnStr, int confirmTextColor, int confirmTextSize,
                                       String cancelBtnStr, int cancelTextColor, int cancelTextSize,
                                       boolean backCancel, View.OnClickListener confirmListener,
                                       View.OnClickListener cancelListener, boolean warning) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog dialog = new MessageDialog(context);

        // 标题
        dialog.setTitleText(title);
        dialog.setTitleTextColor(titleTextColor);
        dialog.setTitleTextSize(titleTextSize);
        dialog.setTitleTextGravity(titleGravity);

        // 内容
        dialog.setMsgText(msg);
        dialog.setMsgTextColor(msgTextColor);
        dialog.setMsgTextSize(msgTextSize);
        dialog.setMsgTextGravity(msgGravity);

        // 取消按钮
        dialog.setCancelBtnText(cancelBtnStr);
        dialog.setCancelBtnTextSize(cancelTextSize);
        dialog.setCancelBtnTextColor(cancelTextColor);

        // 确定按钮
        dialog.setConfirmBtnText(confirmBtnStr);
        dialog.setConfirmBtnTextSize(confirmTextSize);
        dialog.setConfirmBtnTextColor(confirmTextColor);

        // 返回监听
        dialog.setBackPressCancel(backCancel);

        // 监听监听回调
        dialog.setCancelBtnOnClickListener(cancelListener);
        dialog.setConfirmBtnOnClickListener(confirmListener);

        // 如果标题为空默认标题栏隐藏
        if ("".equals(title) || null == title) {
            dialog.setTitleTextVisibility(View.GONE);
        }

        // 只有确定对话框
        if (warning) {
            dialog.setCancelBtnTextVisibility(View.GONE);
        }
        dialog.show();

        return dialog;
    }

    /**
     * 消息提示对话框
     *
     * @param context          上下文
     * @param titleRes         标题
     * @param titleTextColor   标题字体颜色
     * @param titleTextSize    标题字体大小
     * @param titleGravity     标题字体位置
     * @param msgRes           内容
     * @param msgTextColor     内容字体颜色
     * @param msgTextSize      内容字体大小
     * @param msgGravity       内容字体位置
     * @param confirmBtnStrRes 确定按钮
     * @param confirmTextColor 确定按钮字体颜色
     * @param confirmTextSize  确定按钮字体大小
     * @param cancelBtnStrRes  取消按钮
     * @param cancelTextColor  取消按钮字体颜色
     * @param cancelTextSize   取消按钮字体大小
     * @param backCancel       点击返回是否取消对话框[true不取消，false取消]
     * @param confirmlistener  确定监听回调
     * @param cancelListener   取消监听回调
     * @return
     */
    public static Dialog showMsgDialog(Context context, int titleRes,
                                       int titleTextColor, int titleTextSize, int titleGravity,
                                       int msgRes, int msgTextColor, int msgTextSize, int msgGravity,
                                       int confirmBtnStrRes, int confirmTextColor, int confirmTextSize,
                                       int cancelBtnStrRes, int cancelTextColor, int cancelTextSize,
                                       boolean backCancel, View.OnClickListener confirmlistener,
                                       View.OnClickListener cancelListener, boolean warning) {

        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }

        MessageDialog dialog = new MessageDialog(context);

        // 标题
        dialog.setTitleText(titleRes);
        dialog.setTitleTextColor(titleTextColor);
        dialog.setTitleTextSize(titleTextSize);
        dialog.setTitleTextGravity(titleGravity);

        // 内容
        dialog.setMsgText(msgRes);
        dialog.setMsgTextColor(msgTextColor);
        dialog.setMsgTextSize(msgTextSize);
        dialog.setMsgTextGravity(msgGravity);

        // 取消
        dialog.setCancelBtnText(cancelBtnStrRes);
        dialog.setCancelBtnTextSize(cancelTextSize);
        dialog.setCancelBtnTextColor(cancelTextColor);

        // 确定
        dialog.setConfirmBtnText(confirmBtnStrRes);
        dialog.setConfirmBtnTextSize(confirmTextSize);
        dialog.setConfirmBtnTextColor(confirmTextColor);

        // 返回监听
        dialog.setBackPressCancel(backCancel);

        // 按钮监听回调
        dialog.setCancelBtnOnClickListener(cancelListener);
        dialog.setConfirmBtnOnClickListener(confirmlistener);

        // 如果标题为空默认标题栏隐藏
        if (titleRes <= 0) {
            dialog.setTitleTextVisibility(View.GONE);
        }

        // 只有确定对话框
        if (warning) {
            dialog.setCancelBtnTextVisibility(View.GONE);
        }

        dialog.show();

        return dialog;
    }

    /**
     * 消息提示对话框
     *
     * @param context         上下文
     * @param title           标题
     * @param msg             内容
     * @param confirmListener 确定监听回调
     * @return
     */
    public static Dialog showMsgDialog(Context context, String title,
                                       String msg, View.OnClickListener confirmListener) {
        Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
                null, 0, 0, null, 0, 0, false, confirmListener, null, false);
        return dialog;
    }

    /**
     * 消息提示对话框
     *
     * @param context         上下文
     * @param titleRes        标题
     * @param msgRes          内容
     * @param confirmListener 确定监听回调
     * @return
     */
    public static Dialog showMsgDialog(Context context, int titleRes,
                                       int msgRes, View.OnClickListener confirmListener) {
        Dialog dialog = showMsgDialog(context, titleRes, 0, 0, 0, msgRes, 0, 0,
                0, 0, 0, 0, 0, 0, 0, false, confirmListener, null, false);
        return dialog;
    }

    /**
     * 消息提示对话框
     *
     * @param context         上下文
     * @param title           标题
     * @param msg             内容
     * @param confirmListener 确定监听回调
     * @param cancelListener  取消监听回调
     * @return
     */
    public static Dialog showMsgDialog(Context context, String title,
                                       String msg, View.OnClickListener confirmListener,
                                       View.OnClickListener cancelListener) {
        Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
                null, 0, 0, null, 0, 0, false, confirmListener, cancelListener,
                false);
        return dialog;
    }

    /**
     * @param context         上下文
     * @param titleRes        标题
     * @param msgRes          内容
     * @param confirmListener 确定监听回调
     * @param cancelListener  取消监听回调
     * @return
     */
    public static Dialog showMsgDialog(Context context, int titleRes,
                                       int msgRes, View.OnClickListener confirmListener,
                                       View.OnClickListener cancelListener) {
        Dialog dialog = showMsgDialog(context, titleRes, 0, 0, 0, msgRes, 0, 0,
                0, 0, 0, 0, 0, 0, 0, false, confirmListener, cancelListener,
                false);
        return dialog;
    }

    /**
     * @param context         上下文
     * @param title           标题
     * @param msg             内容
     * @param confirmBtnStr   确定
     * @param cancelBtnStr    取消
     * @param confirmListener 确定监听回调
     * @param cancelListener  取消监听回调
     * @return
     */
    public static Dialog showMsgDialog(Context context, String title,
                                       String msg, String confirmBtnStr, String cancelBtnStr,
                                       View.OnClickListener confirmListener,
                                       View.OnClickListener cancelListener) {
        Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
                confirmBtnStr, 0, 0, cancelBtnStr, 0, 0, false,
                confirmListener, cancelListener, false);
        return dialog;
    }

    /**
     * @param context          上下文
     * @param titleRes         标题
     * @param msgRes           内容
     * @param confirmBtnStrRes 确定
     * @param cancelBtnStrRes  取消
     * @param confirmListener  确定监听回调
     * @param cancelListener   取消监听回调
     * @return
     */
    public static Dialog showMsgDialog(Context context, int titleRes,
                                       int msgRes, int confirmBtnStrRes, int cancelBtnStrRes,
                                       View.OnClickListener confirmListener,
                                       View.OnClickListener cancelListener) {
        Dialog dialog = showMsgDialog(context, titleRes, 0, 0, 0, msgRes, 0, 0,
                0, confirmBtnStrRes, 0, 0, cancelBtnStrRes, 0, 0, false,
                confirmListener, cancelListener, false);
        return dialog;
    }

    /**
     * @param context       上下文
     * @param titleRes      标题
     * @param view          自定义view
     * @param confirStr     确定view
     * @param cancelStr     取消
     * @param cancleLister  取消监听器
     * @param confirmLister 确定监听器
     * @return Dialog
     * @throws
     * @Title: showMsgDialog
     * @Description: 自定布局Dialog
     */
    public static Dialog showMsgDialog(Context context, String titleRes,
                                       final View view, String confirStr, String cancelStr,
                                       View.OnClickListener cancleLister,
                                       View.OnClickListener confirmLister) {
        BaseDialog dialog = new BaseDialog(context) {

            @Override
            public View createContentView() {
                return view;
            }
        };
        dialog.setTitleText(titleRes);
        dialog.setCancelBtnText(cancelStr);
        dialog.setConfirmBtnText(confirStr);
        dialog.setConfirmBtnOnClickListener(confirmLister);
        dialog.setCancelBtnOnClickListener(cancleLister);
        dialog.show();
        return dialog;
    }

    /**
     * @param context       上下文
     * @param titleRes      标题
     * @param view          自定义view
     * @param confirStr     确定view
     * @param cancelStr     取消
     * @param cancleLister  取消监听器
     * @param confirmLister 确定监听器
     * @return Dialog
     * @throws
     * @Title: showMsgDialog
     * @Description: 自定布局Dialog
     */
    public static Dialog showMsgDialog(Context context, int titleRes,
                                       final View view, int confirStr, int cancelStr,
                                       View.OnClickListener cancleLister,
                                       View.OnClickListener confirmLister) {
        BaseDialog dialog = new BaseDialog(context) {

            @Override
            public View createContentView() {
                return view;
            }
        };
        dialog.setTitleText(titleRes);
        dialog.setCancelBtnText(cancelStr);
        dialog.setConfirmBtnText(confirStr);
        dialog.setConfirmBtnOnClickListener(confirmLister);
        dialog.setCancelBtnOnClickListener(cancleLister);
        dialog.show();
        return dialog;
    }

    /**
     * @param context       上下文
     * @param titleRes      标题
     * @param view          自定义view
     * @param confirStr     确定view
     * @param confirmLister 确定监听器
     * @return Dialog
     * @throws
     * @Title: showMsgDialog
     * @Description: 自定布局Dialog
     */
    public static Dialog showMsgDialog(Context context, String titleRes,
                                       final View view, String confirStr,
                                       View.OnClickListener confirmLister) {
        BaseDialog dialog = new BaseDialog(context) {

            @Override
            public View createContentView() {
                return view;
            }
        };
        dialog.setTitleText(titleRes);
        dialog.setConfirBtnTextVisibility(View.GONE);
        dialog.setConfirmBtnText(confirStr);
        dialog.setConfirmBtnOnClickListener(confirmLister);
        dialog.show();
        return dialog;
    }

    /**
     * @param context       上下文
     * @param titleRes      标题
     * @param view          自定义view
     * @param confirStr     确定view
     * @param confirmLister 确定监听器
     * @return Dialog
     * @throws
     * @Title: showMsgDialog
     * @Description: 自定布局Dialog
     */
    public static Dialog showMsgDialog(Context context, int titleRes,
                                       final View view, int confirStr,
                                       View.OnClickListener confirmLister) {
        BaseDialog dialog = new BaseDialog(context) {

            @Override
            public View createContentView() {
                return view;
            }
        };
        dialog.setTitleText(titleRes);
        dialog.setCancelBtnTextVisibility(View.GONE);
        dialog.setConfirmBtnText(confirStr);
        dialog.setConfirmBtnOnClickListener(confirmLister);
        dialog.show();
        return dialog;
    }

    /******************* -------END-------- 碇定和取消Dialog -------END-------- *************************/

    /******************* -------START-------- 只有碇定Dialog -------START-------- *************************/
    /**
     * @param context  上下文
     * @param title    标题
     * @param msg      提示内容
     * @param btnStr   按钮
     * @param listener 监听回调
     * @return Dialog
     * @Title: warningDialog
     * @Description: 警告显示对话框
     */
    public static Dialog warningDialog(Context context, String title,
                                       String msg, String btnStr, View.OnClickListener listener) {
        Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
                btnStr, 0, 0, null, 0, 0, false, listener, null, true);
        return dialog;
    }

    /**
     * @param context  上下文
     * @param title    标题
     * @param msg      提示内容
     * @param btnStr   按钮
     * @param listener 监听回调
     * @return Dialog
     * @Title: warningDialog
     * @Description: 警告显示对话框
     */
    public static Dialog warningDialog(Context context, int title, int msg,
                                       int btnStr, View.OnClickListener listener) {
        Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
                btnStr, 0, 0, 0, 0, 0, false, listener, null, true);
        return dialog;
    }

    /**
     * @param context         上下文
     * @param title           标题
     * @param msg             内容
     * @param confirmListener 确定监听回调
     * @return
     */
    public static Dialog warningDialog(Context context, String title,
                                       String msg, View.OnClickListener confirmListener) {
        Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
                null, 0, 0, null, 0, 0, false, confirmListener, null, true);
        return dialog;
    }

    /**
     * @param context         上下文
     * @param titleRes        标题
     * @param msgRes          内容
     * @param confirmListener 确定监听回调
     * @return
     */
    public static Dialog warningDialog(Context context, int titleRes,
                                       int msgRes, View.OnClickListener confirmListener) {
        Dialog dialog = showMsgDialog(context, titleRes, 0, 0, 0, msgRes, 0, 0,
                0, 0, 0, 0, 0, 0, 0, false, confirmListener, null, true);
        return dialog;
    }

    /**
     * @param context         上下文
     * @param title           标题
     * @param msg             内容
     * @param confirmListener 确定监听回调
     * @param backCancel      点击返回是否取消对话框[true不取消，false取消]
     * @return
     */
    public static Dialog warningDialog(Context context, String title,
                                       String msg, View.OnClickListener confirmListener, boolean backCancel) {
        Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
                null, 0, 0, null, 0, 0, backCancel, confirmListener, null, true);
        return dialog;
    }

    /**
     * @param context         上下文
     * @param titleRes        标题
     * @param msgRes          内容
     * @param confirmListener 确定监听回调
     * @param backCancel      点击返回是否取消对话框[true不取消，false取消]
     * @return
     */
    public static Dialog warningDialog(Context context, int titleRes,
                                       int msgRes, View.OnClickListener confirmListener, boolean backCancel) {
        Dialog dialog = showMsgDialog(context, titleRes, 0, 0, 0, msgRes, 0, 0,
                0, 0, 0, 0, 0, 0, 0, backCancel, confirmListener, null, true);
        return dialog;
    }

    /******************* -------END-------- 只有碇定Dialog -------END-------- *************************/

    /******************* -------START-------- 输入框Dialog -------START-------- *************************/
    /**
     *
     * @Title: createEditTextDialog
     * @Description: 输入对话框
     * @param context
     *            上下文
     * @param title
     *            标题
     * @param callBack
     *            监听回调
     * @return void
     * @throws
     */
//    public static void createEditTextDialog(Context context, String title,
//                                            final EditDialogCallBack callBack) {
//        final IEditTextDialog dialog = new IEditTextDialog(context);
//        dialog.setTitleText(title);
//        dialog.setConfirmBtnOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                callBack.result(dialog.getEditText());
//                dimissDialog(dialog);
//            }
//        });
//        dialog.show();
//    }

    /**
     *
     * @Title: createEditTextDialog
     * @Description: 输入对话框
     * @param context
     *            上下文
     * @param title
     *            标题
     * @param callBack
     *            监听回调
     * @return void
     * @throws
     */
//    public static void createEditTextDialog(Context context, int title,
//                                            final EditDialogCallBack callBack) {
////        final IEditTextDialog dialog = new IEditTextDialog(context);
////        dialog.setTitleText(title);
////        dialog.setConfirmBtnOnClickListener(new OnClickListener() {
////
////            @Override
////            public void onClick(View v) {
////                callBack.result(dialog.getEditText());
////                dimissDialog(dialog);
////            }
////        });
////        dialog.show();
//    }

    /**
     * 带有确定与取消的自定义View的Dialog
     * @param activity
     * @param view
     * @param confirmListener
     * @param cancelListener
     * @return
     */
    public static Dialog createDialog(Activity activity, String title,final View view, View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
        if (activity == null) {
            return null;
        }
        if (activity.isFinishing()) {
            return null;
        }
        final BaseDialog dialog = new BaseDialog(activity) {
            @Override
            public View createContentView() {

                return view;
            }
        };
//        dialog.setTitleTextVisibility(View.GONE);
        dialog.setTitleText(title);
        dialog.setCancelBtnOnClickListener(cancelListener);
        dialog.setConfirmBtnOnClickListener(confirmListener);
        dialog.show();
        return dialog;
    }

    /**
     * 只有View的Dialog
     *
     * @param activity
     * @param view
     * @return
     */
    public static Dialog createDialog(Activity activity, final View view) {
        if (activity == null) {
            return null;
        }
        if (activity.isFinishing()) {
            return null;
        }
        BaseDialog dialog = new BaseDialog(activity) {
            @Override
            public View createContentView() {
                return view;
            }
        };
        dialog.setTitleTextVisibility(View.GONE);
        dialog.setBottomLayoutVisibility(View.GONE);
        dialog.show();
        return dialog;
    }

    /**
     * 只有View的Dialog
     * @param activity
     * @param view
     * @return
     */
    public static Dialog createDialog(Activity activity, String title, final View view, View.OnClickListener cancelLister){
        if (activity==null){
            return null;
        }
        if (activity.isFinishing()){
            return null;
        }
        BaseDialog dialog=new BaseDialog(activity) {
            @Override
            public View createContentView() {
                return view;
            }
        };
        dialog.setTitleText(title);
        dialog.setConfirBtnTextVisibility(View.GONE);
        dialog.setCancelBtnOnClickListener(cancelLister);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return  dialog;
    }

    /**
     * 只有View的Dialog
     *
     * @param activity
     * @param view
     * @return
     */
    public static Dialog createDialog(Activity activity, String title, final View view, String cancelStr, View.OnClickListener comfirmListener, View.OnClickListener cancelLister) {
        if (activity == null) {
            return null;
        }
        if (activity.isFinishing()) {
            return null;
        }
        BaseDialog dialog = new BaseDialog(activity) {
            @Override
            public View createContentView() {
                return view;
            }
        };
        dialog.setTitleText(title);
        dialog.setConfirBtnTextVisibility(View.GONE);
        dialog.setConfirmBtnOnClickListener(comfirmListener);
        dialog.setCancelBtnOnClickListener(cancelLister);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelBtnText(cancelStr);
        dialog.show();
        return dialog;
    }


    /******************* -------END-------- 输入框Dialog -------END-------- *************************/

    /******************* -------START-------- 加载Dialog -------START-------- *************************/
    /**
     * 加载对话框
     *
     * @param context
     * @param msg
     * @param backPressCancel
     * @return
     */
    public static Dialog createLoadingDialog(Activity context, String msg,
                                             boolean backPressCancel) {
        if (context==null){
            return null;
        }
        if (context.isFinishing()){
            return null;
        }
        LoadingDialog dialog = new LoadingDialog(context);
        dialog.setBackPressCancel(backPressCancel);
        dialog.setTipText(msg);
        showDialog(dialog);
        return dialog;
    }

    /**
     * 加载对话框
     *
     * @param context
     * @param msgRes
     * @param backPressCancel
     * @return
     */
    public static Dialog createLoadingDialog(Activity context, int msgRes,
                                             boolean backPressCancel) {
        if (context==null){
            return null;
        }
        if (context.isFinishing()){
            return null;
        }
        LoadingDialog dialog = new LoadingDialog(context);
        dialog.setBackPressCancel(backPressCancel);
        dialog.setTipText(msgRes);
        showDialog(dialog);
        return dialog;
    }

    /**
     * 加载对话框
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Activity context, String msg) {
        return createLoadingDialog(context, msg, false);
    }

    /**
     * 加载对话框
     *
     * @param context
     * @param msgRes
     * @return
     */
    public static Dialog createLoadingDialog(Activity context, int msgRes) {
        return createLoadingDialog(context, msgRes, false);
    }

    /**
     * @param context
     * @return
     */
    public static Dialog createLoadingDialog(Activity context) {
        return createLoadingDialog(context, "");
    }

    /******************* -------END-------- 加载Dialog -------EDI-------- *************************/

    /**
     * @param @param dialog
     * @return void
     * @throws
     * @Title: showDialog
     * @Description: 显示对话框
     */
    public static void showDialog(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * @param @param dialog
     * @return void
     * @throws
     * @Title: dimissDialog
     * @Description: 取消对话框
     */
    public static void dimissDialog(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
