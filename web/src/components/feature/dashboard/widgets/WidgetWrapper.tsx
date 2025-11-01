import type { WidgetProps } from '.';
import Widgets from '.';
import WidgetFormWrapper from './WidgetFormWrapper';
import { Button } from '@/components/ui/button';
import { Dialog, DialogContent, DialogTrigger } from '@/components/ui/dialog';
import { cn } from '@/lib/utils';
import { useDashboardActions } from '@/store/dashboard';
import type { WidgetContent } from '@/types/dashboard';
import { AnimatePresence, motion } from 'motion/react';
import { type MouseEventHandler, useState } from 'react';

export default function WidgetWrapper(props: WidgetProps) {
  const widget = Widgets[props.widget.type];

  return (
    <div
      className={cn(
        'rounded-4xl px-3 py-5h-full h-full w-full flex justify-center items-center',
        props.isEditing && 'wiggle',
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
            {props.isEditing && (
              <div className='self-end absolute top-2 right-2'>
                <WidgetControls {...props} />
              </div>
            )}
            <widget.main {...props} />
          </div>
        </motion.div>
      </AnimatePresence>
    </div>
  );
}

function WidgetControls({ id, widget }: { id: string; widget: WidgetContent }) {
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const { updateDraft } = useDashboardActions();

  const Widget = Widgets[widget.type];

  const openConfigModalButtonClickHandler: MouseEventHandler = (event) => {
    setIsDialogOpen(true);
    event.stopPropagation();
  };

  const deleteWidgetButtonClickHandler: MouseEventHandler = (event) => {
    updateDraft((dashboard) => ({
      widgets: dashboard.widgets.filter((widget) => widget.id !== id),
    }));
    event.stopPropagation();
  };

  return (
    <div className='flex items-center gap-2'>
      {Widget.configurable && (
        <Dialog
          open={isDialogOpen}
          onOpenChange={(open) => {
            setIsDialogOpen(open);
          }}
        >
          <DialogTrigger asChild>
            <Button
              variant='ghost'
              icon='edit'
              size='sm'
              onMouseDown={openConfigModalButtonClickHandler}
            />
          </DialogTrigger>

          <DialogContent>
            <WidgetFormWrapper
              widget={widget}
              onSubmit={(content) => {
                updateDraft((dashboard) => ({
                  widgets: dashboard.widgets.map((widget) => {
                    if (widget.id !== id) {
                      return widget;
                    }

                    return {
                      ...widget,
                      ...content,
                    };
                  }),
                }));
                setIsDialogOpen(false);
              }}
            />
          </DialogContent>
        </Dialog>
      )}

      <Button
        variant='ghost'
        icon='trash'
        size='sm'
        onMouseDown={deleteWidgetButtonClickHandler}
      />
    </div>
  );
}
