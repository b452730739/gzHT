// Generated code from Butter Knife. Do not modify!
package com.elegps.module.task_search;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TaskSearchActivity$$ViewBinder<T extends com.elegps.module.task_search.TaskSearchActivity> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2130968787, "field 'taskListView'");
    target.taskListView = finder.castView(view, 2130968787, "field 'taskListView'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.ivSearch = null;
    target.taskListView = null;
  }
}
