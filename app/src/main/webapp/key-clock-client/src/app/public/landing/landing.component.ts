import { Component, OnInit, AfterViewInit, ViewEncapsulation } from '@angular/core';
declare var $: any;
@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LandingComponent implements OnInit, AfterViewInit {

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.initScript();
  }

  initScript() {
    $("body").on("input propertychange", ".floating-label-form-group", function (e) {
      $(this).toggleClass("floating-label-form-group-with-value", !!$(e.target).val());
    }).on("focus", ".floating-label-form-group", function () {
      $(this).addClass("floating-label-form-group-with-focus");
    }).on("blur", ".floating-label-form-group", function () {
      $(this).removeClass("floating-label-form-group-with-focus");
    });
    var MQL = 1170;
    if ($(window).width() > MQL) {
      var headerHeight = $('.navbar-custom').height();
      $(window).on('scroll', {
        previousTop: 0
      },
        function () {
          var currentTop = $(window).scrollTop();
          if (currentTop < this.previousTop) {
            if (currentTop > 0 && $('.navbar-custom').hasClass('is-fixed')) {
              $('.navbar-custom').addClass('is-visible');
            } else {
              $('.navbar-custom').removeClass('is-visible is-fixed');
            }
          } else if (currentTop > this.previousTop) {
            $('.navbar-custom').removeClass('is-visible');
            if (currentTop > headerHeight && !$('.navbar-custom').hasClass('is-fixed')) $('.navbar-custom').addClass('is-fixed');
          }
          this.previousTop = currentTop;
        });
    }
  }

}
