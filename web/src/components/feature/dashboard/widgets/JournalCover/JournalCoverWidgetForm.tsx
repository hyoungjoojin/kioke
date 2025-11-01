import type {
  WidgetFormProps,
  WidgetFormRef,
} from '@/components/feature/dashboard/widgets';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { WidgetType } from '@/constant/dashboard';
import { zodResolver } from '@hookform/resolvers/zod';
import {
  type ForwardedRef,
  forwardRef,
  useImperativeHandle,
  useRef,
} from 'react';
import { useForm } from 'react-hook-form';
import z from 'zod';

const JournalCoverWidgetFormSchema = z.object({
  journalId: z.string().nonempty(),
});

export type JournalCoverWidgetFormSchemaType = z.infer<
  typeof JournalCoverWidgetFormSchema
>;

function JournalCoverWidgetForm(
  { widget, onSubmit }: WidgetFormProps,
  ref: ForwardedRef<WidgetFormRef>,
) {
  const formRef = useRef<HTMLFormElement>(null);
  const form = useForm<JournalCoverWidgetFormSchemaType>({
    resolver: zodResolver(JournalCoverWidgetFormSchema),
    defaultValues: {
      journalId:
        widget.type === WidgetType.JOURNAL_COVER
          ? widget.content.journalId
          : '',
    },
  });

  useImperativeHandle(ref, () => ({
    submit() {
      if (formRef.current) {
        formRef.current.submit();
      }
    },
  }));

  if (widget.type !== WidgetType.JOURNAL_COVER) {
    return null;
  }

  const formSubmitHandler = ({
    journalId,
  }: JournalCoverWidgetFormSchemaType) => {
    onSubmit({
      type: WidgetType.JOURNAL_COVER,
      content: {
        journalId,
      },
    });
  };

  return (
    <Form {...form}>
      <form ref={formRef} onSubmit={form.handleSubmit(formSubmitHandler)}>
        <FormField
          name='journalId'
          control={form.control}
          render={({ field }) => (
            <FormItem>
              <FormLabel>Journal ID</FormLabel>
              <FormControl>
                <Input
                  className='w-full rounded border border-gray-300 px-3 py-2'
                  placeholder='Enter Journal ID'
                  {...field}
                />
              </FormControl>
            </FormItem>
          )}
        />
      </form>
    </Form>
  );
}

const form = forwardRef<WidgetFormRef, WidgetFormProps>(JournalCoverWidgetForm);
export default form;
