# FloatingOrderView
仿美团订单进度悬浮view

## 效果图
![](https://github.com/kuangxiaoguo0123/FloatingOrderView/blob/master/screenshot/orderview.gif)
## 实现
这里我们是继承LinearLayout来实现的。

### 一 重写onMeasure()
定义父view的宽高与子view的宽高相同。
```
 @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = mView.getMeasuredWidth();
        int viewHeight = mView.getMeasuredHeight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, viewWidth,
                    getResources().getDisplayMetrics());
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, viewHeight,
                    getResources().getDisplayMetrics());
        }
        setMeasuredDimension(widthSize, heightSize);
    }
```
### 二 获取父view的宽高
```
@Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mWidth = getWidth();
        mHeight = getHeight();
    }
```
### 三 位移动画
```
	/**
     * view位移动画
     */
    private void initTranslateAnimation() {
        int closeDistance = mWidth - mIconImageView.getWidth() - mView.getPaddingLeft() * 2;
        mTranslateAnimator = ValueAnimator.ofInt(0, closeDistance);
        /*
        当一开始为关闭状态时，给动画设置evaluator，使得动画开始位置为closeDistance
         */
        if (mViewState == ViewStateEnum.CLOSE) {
            mTranslateAnimator.setEvaluator(new TypeEvaluator<Integer>() {
                @Override
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return (int) (endValue - fraction * (endValue - startValue));
                }
            });
        }
        mTranslateAnimator.setDuration(ANIMATION_TIME);
        mTranslateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mTranslateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                /*
                相对于父view的位置坐标
                 */
                mView.layout(value, 0, mWidth, mHeight);
            }
        });
    }
```
### 四 缩放动画
这里点击时x号是有个缩放动画的，可能gif图不是太明显。
```
	/**
     * 点击x号缩放动画
     */
    private void initScaleAnimation() {
        mScaleAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        mScaleAnimator.setDuration(SCALE_ANIMATION_TIME);
        if (mViewState == ViewStateEnum.CLOSE) {
            mScaleAnimator.setEvaluator(new TypeEvaluator<Float>() {
                @Override
                public Float evaluate(float fraction, Float startValue, Float endValue) {
                    return endValue - fraction * (endValue - startValue);
                }
            });
        }
        mScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mCloseImageView.setScaleX(value);
                mCloseImageView.setScaleY(value);
            }
        });
    }
```
### 五 开始动画
```
	/**
     * 使用AnimatorSet让两个动画同时播放
     */
    private void doAnimation() {
        initTranslateAnimation();
        initScaleAnimation();
        AnimatorSet set = new AnimatorSet();
        set.play(mTranslateAnimator).with(mScaleAnimator);
        set.start();
    }
```
## Sample source code
[https://github.com/kuangxiaoguo0123/FloatingOrderView](https://github.com/kuangxiaoguo0123/FloatingOrderView)

## More information
[http://blog.csdn.net/kuangxiaoguo0123/article/details/69063405](http://blog.csdn.net/kuangxiaoguo0123/article/details/69063405)
