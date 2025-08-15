'use client';

import Widgets from '@/components/feature/dashboard/widgets';
import { Button } from '@/components/ui/button';
import { Dialog, DialogContent, DialogTrigger } from '@/components/ui/dialog';
import { IconName } from '@/components/ui/icon';
import {
  DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION,
  DashboardWidgetDimensions,
  DashboardWidgetType,
} from '@/constant/dashboard';
import { cn } from '@/lib/utils';
import { useMyDashboardQuery } from '@/query/dashboard';
import { useDashboard, useDashboardActions } from '@/store/dashboard';
import { Widget } from '@/types/dashboard';
import { useEffect, useState } from 'react';
import ReactGridLayout, { Responsive, WidthProvider } from 'react-grid-layout';

const { columns, breakpoints, margin, rowHeight } =
  DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION;

const ResponsiveGridLayout = WidthProvider(Responsive);

export default function Home() {
  const { data: dashboard } = useMyDashboardQuery();
  const { isEditing, dashboardDraft } = useDashboard();
  const { initDashboard, updateDashboardDraft } = useDashboardActions();

  const [cols, setCols] = useState(columns.md);

  useEffect(() => {
    if (dashboard) {
      initDashboard(dashboard);
    }
  }, [dashboard, initDashboard]);

  const layout = isEditing ? dashboardDraft : dashboard;

  if (!layout) {
    return null;
  }

  const gridLayouts: ReactGridLayout.Layouts = Object.fromEntries(
    Object.entries(columns).map(([size, value]) => [
      size,
      layout.widgets.map((widget) => {
        const type = widget.type;

        return {
          i: widget.id,
          x: (value * widget.x) / 100,
          y: (value * widget.y) / 100,
          ...DashboardWidgetDimensions[type],
          isBounded: true,
          isDraggable: isEditing,
          isResizable: false,
        };
      }),
    ]),
  );

  return (
    <ResponsiveGridLayout
      className='min-h-full'
      layouts={gridLayouts}
      breakpoints={breakpoints}
      cols={columns}
      isBounded={false}
      isResizable={false}
      isDraggable={isEditing}
      isDroppable={isEditing}
      compactType={null}
      verticalCompact={false}
      allowOverlap={false}
      margin={margin}
      rowHeight={rowHeight}
      onDragStop={(_layout, _oldItem, newItem) => {
        updateDashboardDraft((dashboard) => ({
          widgets: dashboard.widgets.map((widget) => {
            if (widget.id !== newItem.i) {
              return widget;
            }

            return {
              ...widget,
              x: (newItem.x * 100) / cols,
              y: newItem.y,
            };
          }),
        }));
      }}
      onBreakpointChange={(_, newCols) => {
        setCols(newCols);
      }}
    >
      {layout.widgets.map((widget) => {
        return (
          <div key={widget.id}>
            <WidgetWrapper widget={widget} isEditing={isEditing} />
          </div>
        );
      })}
    </ResponsiveGridLayout>
  );
}

function WidgetWrapper({
  widget,
  isEditing = false,
}: {
  widget: Widget;
  isEditing?: boolean;
}) {
  const Widget = Widgets[widget.type];

  return (
    <div className='h-full'>
      <div
        className={cn(
          isEditing &&
            'wiggle opacity-75 h-full w-full bg-card rounded-4xl px-3 py-5',
        )}
      >
        {isEditing ? (
          <Widget.preview {...widget.content} />
        ) : (
          <Widget.main {...widget.content} />
        )}
      </div>

      {isEditing && <WidgetPreviewControls id={widget.id} type={widget.type} />}
    </div>
  );
}

function WidgetPreviewControls({
  id,
  type,
}: {
  id: string;
  type: DashboardWidgetType;
}) {
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const { updateDashboardDraft } = useDashboardActions();

  const Widget = Widgets[type];

  return (
    <div className='absolute top-3 right-3 fg-muted flex gap-1'>
      <Dialog
        open={isDialogOpen}
        onOpenChange={(open) => {
          setIsDialogOpen(open);
        }}
      >
        <DialogTrigger asChild>
          <Button
            variant='ghost'
            icon={IconName.EDIT}
            onMouseDown={(event) => {
              setIsDialogOpen(true);
              event.stopPropagation();
            }}
          />
        </DialogTrigger>

        <DialogContent>
          <Widget.edit />
        </DialogContent>
      </Dialog>

      <Button
        variant='ghost'
        icon={IconName.X}
        onMouseDown={(event) => {
          updateDashboardDraft((dashboard) => ({
            widgets: dashboard.widgets.filter((widget) => widget.id !== id),
          }));
          event.stopPropagation();
        }}
      />
    </div>
  );
}
