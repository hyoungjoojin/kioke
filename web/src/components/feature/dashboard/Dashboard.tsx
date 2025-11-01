import WidgetWrapper from './widgets/WidgetWrapper';
import {
  DEFAULT_DASHBOARD_LAYOUT_CONFIGURATION,
  WidgetDimensions,
} from '@/constant/dashboard';
import { useDashboardActions } from '@/store/dashboard';
import type { Dashboard } from '@/types/dashboard';
import { useState } from 'react';
import type ReactGridLayout from 'react-grid-layout';
import { Responsive, WidthProvider } from 'react-grid-layout';

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
          ...WidgetDimensions[type],
          isBounded: true,
          isDraggable: isEditing,
          isResizable: false,
        };
      }),
    ]),
  );

  return (
    <ResponsiveGridLayout
      {...{
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
      }}
    >
      {dashboard.widgets.map((widget) => {
        return (
          <div key={widget.id}>
            <WidgetWrapper
              id={widget.id}
              isEditing={isEditing}
              widget={widget}
            />
          </div>
        );
      })}
    </ResponsiveGridLayout>
  );
}
