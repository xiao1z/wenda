<#include "header.ftl">
	<div class="zu-global-notify" id="zh-global-message" style="display:none">
        <div class="zg-wrap">
            <div class="zu-global-nitify-inner">
            <a class="zu-global-notify-close" href="javascript:;" title="关闭" name="close">x</a>
            <span class="zu-global-notify-icon"></span>
            <span class="zu-global-notify-msg"></span>
            </div>
        </div>
    </div>
    <div class="zg-wrap zu-main clearfix " role="main">
        <div class="zu-main-content">
            <div class="zu-main-content-inner">
                <div class="zg-section" id="zh-home-list-title">
                    <i class="zg-icon zg-icon-feedlist"></i>最新动态
                    <input type="hidden" id="is-topstory">
                    <span class="zg-right zm-noti-cleaner-setting" style="list-style:none">
                        <a href="https://nowcoder.com/settings/filter" class="zg-link-gray-normal">
                            <i class="zg-icon zg-icon-settings"></i>设置</a></span>
                </div>
                <div class="zu-main-feed-con navigable" data-feedtype="topstory" id="zh-question-list" data-widget="navigable" data-navigable-options="{&quot;items&quot;:&quot;&gt; .zh-general-list .feed-content&quot;,&quot;offsetTop&quot;:-82}">
                    <a href="javascript:;" class="zu-main-feed-fresh-button" id="zh-main-feed-fresh-button" style="display:none"></a>
                    <div id="js-home-feed-list" class="zh-general-list topstory clearfix" data-init="{&quot;params&quot;: {}, &quot;nodename&quot;: &quot;TopStory2FeedList&quot;}" data-delayed="true" data-za-module="TopStoryFeedList">
                        
                        
                        
                        
                       <#list voList as vo>
                       
						<div class="feed-item folding feed-item-hook feed-item-1
                        " feed-item-p="" data-type="p" id="feed-1" data-za-module="FeedItem" data-za-index="">
                            <meta itemprop="ZReactor" data-id="113477" data-meta="{&quot;source_type&quot;: &quot;promotion_article&quot;, &quot;voteups&quot;: 1082, &quot;comments&quot;: 100, &quot;source&quot;: []}">
                            <div class="feed-item-inner">
                                <div class="avatar">
                                    <a title="${vo.user.username}" data-tip="p$t$zhao-yong-feng" class="zm-item-link-avatar" target="_blank" href="https://nowcoder.com/people/zhao-yong-feng">
                                        <img src="${vo.user.headUrl}" class="zm-item-img-avatar"></a>
                                </div>
                                <div class="feed-main">
                                    <div class="feed-content" data-za-module="PostItem">
                                        <meta itemprop="post-id" content="113477">
                                        <meta itemprop="post-url-token" content="19831487">
                                        <h2 class="feed-title">
                                            <a target="_blank" class="post-link" href="http://zhuanlan.zhihu.com/p/19831487">${vo.question.title}</a></h2>
                                        <div class="entry-body post-body js-collapse-body">
                                            <div class="zm-item-vote">
                                                <a class="zm-item-vote-count js-expand js-vote-count" href="#" data-bind-votecount="">1082</a></div>
                                            <div class="zm-votebar" data-za-module="VoteBar">
                                                <button class="up" aria-pressed="false" title="赞同">
                                                    <i class="icon vote-arrow"></i>
                                                    <span class="count">1082</span>
                                                    <span class="label sr-only">赞同</span></button>
                                            </div>
                                            <div class="author-info">
                                                <a href="/wenda/user/${vo.user.id}" data-tip="p$t$zhao-yong-feng" class="name">${vo.user.username}</a>, ${vo.question.createdDate?string('yyyy年MM月dd日  HH:mm:ss')}</div>
                                            <div class="zm-item-vote-info" data-votecount="1082" data-za-module="VoteInfo">
                                                <span class="voters text">
                                                    <a href="#" class="more text">
                                                        <span class="js-voteCount">1082</span>&nbsp;人赞</a></span>
                                            </div>
                                            <div class="post-content" data-author-name="赵永峰" data-entry-url="http://zhuanlan.zhihu.com/p/19831487">
                                                ${vo.question.content}
                                            </div>
                                        </div>
                                        <div class="feed-meta">
                                            <div class="zm-item-meta meta clearfix js-contentActions">
                                                <div class="zm-meta-panel">
                                                    <a data-follow="c:link" class="zg-follow meta-item" href="#" id="cl-2180">
                                                        <i class="z-icon-follow"></i>关注专栏</a>
                                                    <a href="#" class="meta-item toggle-comment js-toggleCommentBox">
                                                        <i class="z-icon-comment"></i>${vo.question.commentCount} 条评论</a>
                                                    <a href="#" class="meta-item zu-autohide js-share goog-inline-block goog-menu-button" role="button" aria-expanded="false" tabindex="0" aria-haspopup="true" style="-webkit-user-select: none;">
                                                        <div class="goog-inline-block goog-menu-button-outer-box">
                                                            <div class="goog-inline-block goog-menu-button-inner-box">
                                                                <div class="goog-inline-block goog-menu-button-caption">
                                                                    <i class="z-icon-share"></i>分享</div>
                                                                <div class="goog-inline-block goog-menu-button-dropdown">&nbsp;</div></div>
                                                        </div>
                                                    </a>
                                                    <span class="zg-bull zu-autohide">•</span>
                                                    <a href="#" class="meta-item zu-autohide js-report">举报</a>
                                                    <button class="meta-item item-collapse js-collapse">
                                                        <i class="z-icon-fold"></i>收起</button>
                                                </div>
                                            </div>
                                            <a href="#" class="ignore zu-autohide" name="dislike" data-tip="s$b$不感兴趣"></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="undo-dislike-options" data-item_id="1">此内容将不会在动态中再次显示
                                <span class="zg-bull">•</span>
                                <a href="#" class="meta-item revert">撤销</a>
                                <a href="#" class="ignore zu-autohide close"></a>
                            </div>
                        </div>
                       
                    </div>
					</#list>

					
					
					
					
					
					
					
					
					
					
					
                    <a href="javascript:;" id="zh-load-more" data-method="next" class="zg-btn-white zg-r3px zu-button-more" style="">更多</a></div>
            </div>
        </div>
    </div>
    <script type="text/json" class="json-inline" data-name="guiders2">{
            "exclusive-popover": {},
            "section": {
                "home-topstory": {
                    "content": "\u5173\u6ce8\u4e00\u4e9b\u8bdd\u9898\u548c\u4eba\uff0c\u8fd9\u91cc\u4f1a\u6709\u66f4\u591a\u4f60\u611f\u5174\u8da3\u7684\u5185\u5bb9",
                    "dismissText": "\u77e5\u9053\u4e86",
                    "title": "\u6700\u65b0\u52a8\u6001"
                }
            },
            "editor": ["bio", "topic"]
        }</script>
    <script type="text/template" id="tmpl-Guide-BioEditor">< div class = "Guide-BioEditor" > <div class = "Guide-header" > <h2 class = "Guide-title" > 你的职业或专业是什么？ < /h2>
    <p class="Guide-subtitle">简单介绍自己，会为你挑选你可能感兴趣话题</p ></div>
    <div class="Guide-content">
    <div class="Guide-BioEditorForm">
    <button class="Guide-BioEditorButton Button Button-flatPrimary js-submit">完成</button > <div class = "Guide-BioEditorInputWrapper" > <input class = "Guide-BioEditorInput Input"name = "bio"placeholder = "比如医生、律师、设计师" > </div></div > <div class = "Guide-BioEditorUsers" > <p class = "Guide-BioEditorDescription" > 他们是这样介绍自己的 < /p>
    <ul>
    <li class="Guide-BioEditorItem Guide-BioEditorItem--first">
    <img class="Guide-BioEditorAvatar" src="https:/ / pic3.zhimg.com / 7f5966584a3301d83ed24708d9d56aea_l.jpg " width="50 " height="50 ">
    <span class="Guide - BioEditorBio ">系外行星的研究生</span></li>
    <li class="Guide - BioEditorItem Guide - BioEditorItem--second ">
    <img class="Guide - BioEditorAvatar " src="https: //pic2.zhimg.com/6e5a78fe9_l.jpg" width="50" height="50">
        < span class = "Guide-BioEditorBio" > Lonely Planet作者，潜水员 < /span></li > <li class = "Guide-BioEditorItem Guide-BioEditorItem--third" > <img class = "Guide-BioEditorAvatar"src = "https://pic2.zhimg.com/02295607d866419c478062d9bfc5a041_l.jpg"width = "50"height = "50" > <span class = "Guide-BioEditorBio" > 北京大学南亚学系 < /span></li > <li class = "Guide-BioEditorItem Guide-BioEditorItem--fourth" > <img class = "Guide-BioEditorAvatar"src = "https://pic3.zhimg.com/47e67af4e15cb97d47da376d1b83cae6_l.jpg"width = "50"height = "50" > <span class = "Guide-BioEditorBio" > 机械工程师，pegasister < /span></li > <li class = "Guide-BioEditorItem Guide-BioEditorItem--fifth" > <img class = "Guide-BioEditorAvatar"src = "https://pic3.zhimg.com/f374969469745635565a63bcd36842f6_l.jpg"width = "50"height = "50" > <span class = "Guide-BioEditorBio" > 钛度科技 / 电子竞技 < /span></li > <li class = "Guide-BioEditorItem Guide-BioEditorItem--sixth" > <img class = "Guide-BioEditorAvatar"src = "https://pic2.zhimg.com/fe9e9e6dd_l.jpg"width = "50"height = "50" > <span class = "Guide-BioEditorBio" > 工科女博士 / 专业西点师 / 东京时差党 < /span></li > </ul>
    </div > </div>
    </div > </script>
    <script type="text/template" id="tmpl-Guide-Topics">< div class = "Guide-Topics" > <div class = "Guide-header" > <h2 class = "Guide-title" > 你想关注哪些话题？ < /h2>
    <p class="Guide-subtitle">我们将根据你关注的话题定制首页推荐内容</p ></div>
    <div class="Guide-content">
    <div class="Guide-topicsLoading">
    <span class="Spinner"></span > <p > 正在加载中 < /p></div > </div>
    <div class="Guide-TopicsFooter GaussianBlur">
    <button class="Guide-TopicsSubmit Button Button-flatPrimary js-submit">进入牛客</button > </div></div > </script>
    <script type="text/template" class="activate-dialog-template">< div class = "code-activate-view clearfix" > <div class = "tip" > 为了正常使用牛客的提问、回答、评论和编辑功能，请绑定你的手机或邮箱。 < /div>
    <form class="send-code">
    <div class="input-wrapper">
    <label class="info" for="account">手机或邮箱：</label > <input type = "text"name = "account"class = "text"placeholder = "手机号或邮箱"required ></div>
    <button class="submit zg-btn-blue">获取验证码</button ></form>
    <form class="activate">
    <div class="input-wrapper">
    <label class="info" for="account">手机或邮箱：</label > <input type = "text"name = "account"class = "text"placeholder = "手机号或邮箱"required ></div>
    <div class="input-wrapper verification-code">
    <label class="info" for="digits">验证码：</label > <input type = "text"name = "digits"class = "text"placeholder = "6 位数验证码"maxlength = "6"required > <button type = "button"class = "resend-code" > 重发验证码 < /button></div > <button class = "submit zg-btn-blue" > 激活 < /button></form > </div>

    /</script>
    <script type="text/json" class="json-inline" data-name="current_user">["\u80e1\u539f", "hu-yuan-24-48", "https:\/\/pic1.zhimg.com\/da8e974dc_s.jpg", "69e344deb3bd4d35db07194565261bbe", "", 0, 0, false, null, "", 0, true, false, false, "734705672150609920", false]</script>
    <script type="text/json" class="json-inline" data-name="user_status">[null, null]</script>
    <script type="text/json" class="json-inline" data-name="env">["zhihu.com", "comet.zhihu.com", false, null, false]</script>
    <script type="text/json" class="json-inline" data-name="permissions">[]</script>
    <script type="text/json" class="json-inline" data-name="ga_vars">{
            "user_created": 1467947482000,
            "now": 1467947482000,
            "abtest_mask": "---------1--------------------",
            "user_attr": [1, 0, 0, "-", "-"],
            "user_hash": "69e344deb3bd4d35db07194565261bbe"
        }</script>
    <script type="text/json" class="json-inline" data-name="ra-urls">{
            "Report": "https:\/\/static.zhihu.com\/static\/revved\/-\/apps\/Report.d6e20840.js",
            "Community": "https:\/\/static.zhihu.com\/static\/revved\/-\/apps\/Community.4e11c449.js",
            "OrgAnalytics": "https:\/\/static.zhihu.com\/static\/revved\/-\/apps\/OrgAnalytics.47e1a483.js",
            "common": "https:\/\/static.zhihu.com\/static\/revved\/-\/apps\/common.ed1d17c3.js",
            "CommentApp": "https:\/\/static.zhihu.com\/static\/revved\/-\/apps\/CommentApp.2ab02258.js"
        }</script>
    <script src="/wenda/static/scripts/vendor.ff76fbae.js"></script>
    <script src="/wenda/static/scripts/base.fb262f0e.js"></script>
    <script src="/wenda/static/scripts/common.fed5411f.js"></script>
    <script src="/wenda/static/scripts/richtexteditor.aa0a4005.js" async=""></script>
    <script src="/wenda/static/scripts/page-main.5d4c554d.js"></script>
    <meta name="entry" content="ZH.entryHome" data-module-id="page-main">
    <script type="text/zscript" znonce="d3edc464cf014708819feffde7ddd01e"></script>
    <input type="hidden" name="_xsrf" value="21aa1c8d254df2899b23ab9afbd62a53">
    <div class="zh-backtotop" style="opacity: 0; display: none;">
        <a data-action="backtotop" data-tip="s$r$回到顶部" href="javascript:;" class="btn-backtotop btn-action">
            <div class="arrow"></div>
            <div class="stick"></div>
        </a>
    </div>
    <div id="zh-tooltip" data-theme="" class="tooltip bottom" style="visibility: hidden; left: 778px; top: 321px; display: none;">
        <div class="tooltip-arrow"></div>
        <div class="tooltip-inner content-prewrap">不感兴趣</div></div>
    <div class="copyright-notification">
        <div class="user-image-container">
            <img class="user-image" src="https://nowcoder.com/"></div>
        <div class="tooltip right noty-tooltip">
            <div class="tooltip-arrow arrow"></div>
            <div class="tooltip-inner inner">如需转载，请通过私信或评论联系我</div></div>
    </div>
<#include "footer.ftl">