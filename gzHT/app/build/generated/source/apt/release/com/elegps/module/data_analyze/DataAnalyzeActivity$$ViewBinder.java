// Generated code from Butter Knife. Do not modify!
package com.elegps.module.data_analyze;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DataAnalyzeActivity$$ViewBinder<T extends com.elegps.module.data_analyze.DataAnalyzeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968697, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = finder.castView(view, 2130968697, "field 'ivBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked();
        }
      });
    view = finder.findRequiredView(source, 2130968814, "field 'tvPrevMonthProduction'");
    target.tvPrevMonthProduction = finder.castView(view, 2130968814, "field 'tvPrevMonthProduction'");
    view = finder.findRequiredView(source, 2130968815, "field 'tvPrevMonthStock'");
    target.tvPrevMonthStock = finder.castView(view, 2130968815, "field 'tvPrevMonthStock'");
    view = finder.findRequiredView(source, 2130968813, "field 'tvPrevMonthPerHour'");
    target.tvPrevMonthPerHour = finder.castView(view, 2130968813, "field 'tvPrevMonthPerHour'");
    view = finder.findRequiredView(source, 2130968811, "field 'tvNextMonthProduction'");
    target.tvNextMonthProduction = finder.castView(view, 2130968811, "field 'tvNextMonthProduction'");
    view = finder.findRequiredView(source, 2130968812, "field 'tvNextMonthStock'");
    target.tvNextMonthStock = finder.castView(view, 2130968812, "field 'tvNextMonthStock'");
    view = finder.findRequiredView(source, 2130968810, "field 'tvNextMonthPerHour'");
    target.tvNextMonthPerHour = finder.castView(view, 2130968810, "field 'tvNextMonthPerHour'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvPrevMonthProduction = null;
    target.tvPrevMonthStock = null;
    target.tvPrevMonthPerHour = null;
    target.tvNextMonthProduction = null;
    target.tvNextMonthStock = null;
    target.tvNextMonthPerHour = null;
  }
}
