import Widgets, { type WidgetFormProps, type WidgetFormRef } from '.';
import { Button } from '@/components/ui/button';
import { DialogTitle } from '@/components/ui/dialog';
import { useRef } from 'react';

export default function WidgetFormWrapper({
  widget,
  onSubmit,
}: WidgetFormProps) {
  const formRef = useRef<WidgetFormRef>(null);

  const Widget = Widgets[widget.type];
  if (!Widget.configurable) {
    return null;
  }

  const submitFormButtonClickHandler = () => {
    if (formRef.current) {
      formRef.current.submit();
    }
  };

  return (
    <>
      <DialogTitle>Fuck</DialogTitle>
      <Widget.form ref={formRef} onSubmit={onSubmit} widget={widget} />
      <Button onClick={submitFormButtonClickHandler}>Submit</Button>
    </>
  );
}
