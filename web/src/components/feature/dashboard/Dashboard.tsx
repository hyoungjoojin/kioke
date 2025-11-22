import Widgets from './widgets';
import WidgetControls from './widgets/WidgetControls';
import { ScrollArea } from '@/components/ui/scroll-area';
import {
  DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION,
  WidgetDimensions,
} from '@/constant/dashboard';
import { cn } from '@/lib/utils';
import { useDashboardActions } from '@/store/dashboard';
import type { Dashboard, Widget } from '@/types/dashboard';
import { AnimatePresence, motion } from 'motion/react';
import { useMemo, useState } from 'react';
import type ReactGridLayout from 'react-grid-layout';
import { Responsive, WidthProvider } from 'react-grid-layout';

const {
  columns: cols,
  breakpoints,
  margin,
} = DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION;

interface DashboardProps {
  widgets: Widget[];
  isEditing?: boolean;
}

const ResponsiveGridLayout = WidthProvider(Responsive);

function Dashboard({ widgets, isEditing = false }: DashboardProps) {
  const { updateDraft } = useDashboardActions();
  const [width, setWidth] = useState(0);
  const [columns, setColumns] = useState(cols.md);

  const layouts: ReactGridLayout.Layouts = useMemo(
    () =>
      Object.fromEntries(
        Object.entries(cols).map(([size, value]) => [
          size,
          widgets.map((widget) => {
            const type = widget.type;

            return {
              i: widget.id,
              x: (value * widget.x) / 100,
              y: widget.y,
              ...WidgetDimensions[type],
              isBounded: true,
              isDraggable: isEditing,
              isResizable: false,
            };
          }),
        ]),
      ),
    [isEditing, widgets],
  );

  return (
    <ScrollArea type='always' className='h-full w-full flex'>
      <ResponsiveGridLayout
        rowHeight={columns !== 0 ? width / columns : 0}
        {...{
          cols,
          breakpoints,
          margin,
          layouts,
          compactType: null,
          allowOverlap: false,
          isBounded: false,
          isResizable: false,
          isDraggable: isEditing,
          isDroppable: isEditing,
          onWidthChange: (width) => {
            setWidth(width);
          },
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
        }}
      >
        {widgets.map((widget) => {
          const w = Widgets[widget.type];

          return (
            <div key={widget.id} className='relative'>
              <div
                className={cn(
                  'rounded-4xl h-full w-full flex justify-center items-center',
                  isEditing && 'wiggle',
                )}
              >
                <AnimatePresence>
                  <motion.div
                    initial={{ opacity: 0 }}
                    animate={{ opacity: 1 }}
                    exit={{ opacity: 0 }}
                    className='h-full w-full'
                  >
                    <div className='h-full w-full flex flex-col items-center justify-center'>
                      <w.main widget={widget} isPreview={false} />
                    </div>
                  </motion.div>
                </AnimatePresence>
              </div>

              {isEditing && <WidgetControls widget={widget} />}
            </div>
          );
        })}
      </ResponsiveGridLayout>
    </ScrollArea>
  );
}

export default Dashboard;
