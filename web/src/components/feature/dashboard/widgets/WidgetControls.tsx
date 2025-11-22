import type { WidgetFormRef } from '.';
import Widgets from '.';
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { useDashboardActions } from '@/store/dashboard';
import type { Widget } from '@/types/dashboard';
import { type MouseEventHandler, useRef, useState } from 'react';

interface WidgetControlsProps {
  widget: Widget;
}

function WidgetControls({ widget }: WidgetControlsProps) {
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const { updateDraft } = useDashboardActions();
  const formRef = useRef<WidgetFormRef>(null);

  const Widget = Widgets[widget.type];

  const openConfigModalButtonClickHandler: MouseEventHandler = (event) => {
    setIsDialogOpen(true);
    event.stopPropagation();
  };

  const deleteWidgetButtonClickHandler: MouseEventHandler = (event) => {
    updateDraft((dashboard) => ({
      widgets: dashboard.widgets.filter((widget) => widget.id !== widget.id),
    }));
    event.stopPropagation();
  };

  const submitFormButtonClickHandler = () => {
    if (formRef.current) {
      formRef.current.submit();
    }
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
            <DialogHeader>
              <DialogTitle>{widget.type}</DialogTitle>
            </DialogHeader>

            <Widget.form
              ref={formRef}
              onSubmit={(content) => {
                updateDraft((dashboard) => ({
                  widgets: dashboard.widgets.map((w) => {
                    if (w.id !== widget.id) {
                      return w;
                    }

                    return {
                      ...w,
                      ...content,
                    };
                  }),
                }));
                setIsDialogOpen(false);
              }}
              content={widget}
            />
            <Button
              onClick={() => {
                submitFormButtonClickHandler();
              }}
            >
              Submit
            </Button>
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

export default WidgetControls;
