// Generated code from Butter Knife. Do not modify!
package com.elegps.module.work_hours;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WorkHoursSearchActivity$$ViewBinder<T extends com.elegps.module.work_hours.WorkHoursSearchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968697, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = finder.castView(view, 2130968697, "field 'ivBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2130968698, "field 'ivSearch' and method 'onViewClicked'");
    target.ivSearch = finder.castView(view, 2130968698, "field 'ivSearch'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2130968803, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2130968803, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2130968802, "field 'tvHoursStatis'");
    target.tvHoursStatis = finder.castView(view, 2130968802, "field 'tvHoursStatis'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.ivSearch = null;
    target.tvTitle = null;
    target.tvHoursStatis = null;
  }
}
