'use client';

import Widgets from '@/components/feature/dashboard/widgets';
import { Button } from '@/components/ui/button';
import { Dialog, DialogContent, DialogTrigger } from '@/components/ui/dialog';
import { IconName } from '@/components/ui/icon';
import type { DashboardWidgetType } from '@/constant/dashboard';
import {
  DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION,
  DashboardWidgetDimensions,
} from '@/constant/dashboard';
import { cn } from '@/lib/utils';
import { useMyDashboardQuery } from '@/query/dashboard';
import { useDashboard, useDashboardActions } from '@/store/dashboard';
import type { Widget } from '@/types/dashboard';
import { AnimatePresence, motion } from 'motion/react';
import { useEffect, useState } from 'react';
import type ReactGridLayout from 'react-grid-layout';
import { Responsive, WidthProvider } from 'react-grid-layout';

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
      layouts={gridLayouts}
      breakpoints={breakpoints}
      cols={columns}
      isBounded={false}
      isResizable={false}
      isDraggable={isEditing}
      isDroppable={isEditing}
      compactType={null}
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
          'bg-card rounded-4xl px-3 py-5 shadow-xl h-full w-full',
          isEditing && 'wiggle opacity-75',
        )}
      >
        {isEditing ? (
          <div className='flex flex-col'>
            <div className='self-end'>
              <WidgetPreviewControls
                id={widget.id}
                type={widget.type}
                content={widget.content}
              />
            </div>
            <Widget.preview {...widget.content} />
          </div>
        ) : (
          <AnimatePresence>
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              className='h-full w-full'
            >
              <div className='h-full w-full flex flex-col items-center justify-center'>
                <Widget.main {...widget.content} />
              </div>
            </motion.div>
          </AnimatePresence>
        )}
      </div>
    </div>
  );
}

function WidgetPreviewControls({
  id,
  content,
  type,
}: {
  id: string;
  content: any;
  type: DashboardWidgetType;
}) {
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const { updateDashboardDraft } = useDashboardActions();

  const Widget = Widgets[type];

  return (
    <div className='flex'>
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
          <Widget.edit
            content={content}
            onSubmit={(content) => {
              updateDashboardDraft((dashboard) => ({
                widgets: dashboard.widgets.map((widget) => {
                  if (widget.id !== id) {
                    return widget;
                  }

                  return {
                    ...widget,
                    content,
                  };
                }),
              }));

              setIsDialogOpen(false);
            }}
          />
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
