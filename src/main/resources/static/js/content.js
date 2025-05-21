function initArticeMenu() {
	$(function() {
		var $blogContentBody = $('#tab-should-know');
		var $h = $blogContentBody.find('h2, h3');
		var $articleBox = $('.article-module');
		var $articleMenu = $('#article-menu');
		if ($blogContentBody && $blogContentBody[0]) {
			var padding = [ 0, 10, 20, 30, 40 ];
			var liDom, aDom;
			var hasMenu = false;

			// 目录导航定位
			$(window)
					.scroll(
							function() {
								var top = $(document).scrollTop();
								var currentId = ""; // 滚动条现在所在位置的item id
								$h.each(function() {
									var $this = $(this);
									// 注意：$this.offset().top代表每一个item的顶部位置
									if (top > $this.offset().top - 200) {
										currentId = "#" + $this.prev().attr("id");
									} else {
										return false;
									}
								});

								var currentLink = $articleMenu.find(".active");
								if (currentId&& currentLink.attr("href") != currentId) {
									currentLink.removeClass("active");
									$articleMenu.find("[href=" + currentId + "]").parent().addClass("active");
								}
							});
			// 生成目录
			$h.each(function(index) {
						var $this = $(this);
						var tagText = $this.text().trim();
						var tagName = $this[0].tagName.toLowerCase();
						var tagIndex = parseInt(tagName.charAt(1)) - 1;

						$this.addClass("menu-title").before(
								$('<span id="menu_' + index + '" class="menu-point"></span>'));
						aDom = '<a href="#menu_'
								+ index
								+ '" style="display:inline-block;"><i class="fa fa-caret-right"></i>'
								+ '  ' + tagText + '</a>';
						liDom = '<li style="padding-left:' + padding[tagIndex]
								+ 'px;">' + aDom + '</li>';
						$articleMenu.find("ul").append(liDom);
						hasMenu = true;
					});
			if (hasMenu) {
				$articleMenu.show();
				$articleBox.removeClass('hide');
				bindMenuScroll(), $(window).scroll(function() {
					bindMenuScroll();
				});

				function bindMenuScroll() {
					$articleBox.css('display', ($(window).width() < 768) ? 'none' : 'block');
					if ($(window).width() < 768) {
						return false;
					}
						if ($(document).scrollTop() >= 200) {
							if (!$articleBox.hasClass("fixed")) {
								$articleBox.addClass('fixed').css({
									//width : '25.2%'
								}).animate({
									top : '85px'
								}, 100);
								$('.close-article-menu').removeClass('hide');
							}
						} else {
							$articleBox.removeClass('fixed').removeAttr('style');
							$('.close-article-menu').addClass('hide');
						}
				}

				$('.close-article-menu').click(function() {
					$articleBox.addClass('hide');
				});
			}

		}
	});
}

$(function() {
	initArticeMenu();

	$("img.lazy-img").lazyload({
		placeholder : "/img/loading.gif",
		effect : "fadeIn",
		threshold : 100
	});
	$(window).bind("load", function() {
		var timeout = setTimeout(function() {
			$("img.lazy-img").trigger("sporty");
		}, 3000);
	});
});