import Widgets from './widgets';
import { Button } from '@/components/ui/button';
import { Dialog, DialogContent, DialogTrigger } from '@/components/ui/dialog';
import { IconName } from '@/components/ui/icon';
import type { DashboardWidgetType } from '@/constant/dashboard';
import {
  DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION,
  DashboardWidgetDimensions,
} from '@/constant/dashboard';
import { cn } from '@/lib/utils';
import { useDashboardActions } from '@/store/dashboard';
import type { Dashboard } from '@/types/dashboard';
import { AnimatePresence, motion } from 'motion/react';
import { useState } from 'react';
import type ReactGridLayout from 'react-grid-layout';
import {
  Responsive,
  type ResponsiveProps,
  WidthProvider,
  type WidthProviderProps,
} from 'react-grid-layout';

const {
  columns: cols,
  breakpoints,
  margin,
  rowHeight,
} = DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION;

interface DashboardProps {
  dashboard?: Dashboard;
  isEditing: boolean;
}

const ResponsiveGridLayout = WidthProvider(Responsive);

export default function Dashboard({ dashboard, isEditing }: DashboardProps) {
  const { updateDraft } = useDashboardActions();
  const [columns, setColumns] = useState(cols.md);

  if (!dashboard) {
    return null;
  }

  const layouts: ReactGridLayout.Layouts = Object.fromEntries(
    Object.entries(cols).map(([size, value]) => [
      size,
      dashboard.widgets.map((widget) => {
        const type = widget.type;

        return {
          i: widget.id,
          x: (value * widget.x) / 100,
          y: widget.y,
          ...DashboardWidgetDimensions[type],
          isBounded: true,
          isDraggable: isEditing,
          isResizable: false,
        };
      }),
    ]),
  );

  const gridLayoutProps: Partial<ResponsiveProps & WidthProviderProps> = {
    cols,
    breakpoints,
    margin,
    rowHeight,
    layouts,
    compactType: null,
    allowOverlap: false,
    isBounded: false,
    isResizable: false,
    isDraggable: isEditing,
    isDroppable: isEditing,
    onBreakpointChange: (_, newCols) => {
      setColumns(newCols);
    },
    ...(isEditing && {
      onDragStop: (_layout, _oldItem, newItem) => {
        updateDraft((dashboard) => ({
          widgets: dashboard.widgets.map((widget) => {
            if (widget.id !== newItem.i) {
              return widget;
            }

            return {
              ...widget,
              x: (newItem.x * 100) / columns,
              y: newItem.y,
            };
          }),
        }));
      },
    }),
  };

  return (
    <ResponsiveGridLayout {...gridLayoutProps}>
      {dashboard.widgets.map((widget) => {
        const Widget = Widgets[widget.type];

        return (
          <div key={widget.id}>
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
          </div>
        );
      })}
    </ResponsiveGridLayout>
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

  const { updateDraft } = useDashboardActions();

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
              updateDraft((dashboard) => ({
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
          updateDraft((dashboard) => ({
            widgets: dashboard.widgets.filter((widget) => widget.id !== id),
          }));
          event.stopPropagation();
        }}
      />
    </div>
  );
}
