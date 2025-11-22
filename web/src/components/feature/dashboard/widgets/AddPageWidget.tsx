import type { WidgetFormProps, WidgetFormRef, WidgetProps } from '.';
import { useGetJournalsQuery } from '@/app/api/journals/query';
import { Button } from '@/components/ui/button';
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from '@/components/ui/command';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from '@/components/ui/form';
import Icon from '@/components/ui/icon';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Spinner } from '@/components/ui/spinner';
import { WidgetType } from '@/constant/dashboard';
import { Routes } from '@/constant/routes';
import useCreateJournalMutation from '@/hooks/query/useCreateJournalMutation';
import useGetJournalByIdQuery from '@/hooks/query/useGetJournalByIdQuery';
import { useCreatePageMutationQuery } from '@/query/page';
import type { WidgetContent } from '@/types/dashboard';
import { zodResolver } from '@hookform/resolvers/zod';
import { useIntersectionObserver } from '@uidotdev/usehooks';
import { useRouter } from 'next/navigation';
import {
  type ForwardedRef,
  forwardRef,
  useEffect,
  useImperativeHandle,
  useRef,
  useState,
} from 'react';
import { useForm } from 'react-hook-form';
import z from 'zod';

function AddPageWidget({ widget }: WidgetProps) {
  if (widget.type !== WidgetType.ADD_PAGE) {
    throw new Error();
  }

  const router = useRouter();

  const { data: journal } = useGetJournalByIdQuery({
    path: {
      journalId: widget.content.journalId,
    },
  });

  const { mutate: createPage, isPending } = useCreatePageMutationQuery();

  return (
    <Button
      variant='ghost'
      className='h-full w-full flex items-center justify-center border border-border shadow-sm'
      onClick={() => {
        createPage(
          {
            journalId: widget.content.journalId,
            title: 'New Page',
            date: new Date(),
          },
          {
            onSuccess: (page) => {
              router.push(Routes.PAGE(page.pageId));
            },
          },
        );
      }}
    >
      {journal ? (
        <div className='flex flex-col items-center justify-center'>
          {isPending ? <Spinner /> : <Icon name='plus' />}
          <div className='font-bold'>{journal.title}</div>
        </div>
      ) : (
        <Spinner />
      )}
    </Button>
  );
}

const AddPageWidgetFormSchema = z.object({
  journalId: z.string().nonempty('Journal ID is required'),
});
type AddPageWidgetFormValues = z.infer<typeof AddPageWidgetFormSchema>;

const AddPageWidgetConfigForm = forwardRef(function AddPageWidgetConfig(
  { content, onSubmit }: WidgetFormProps,
  ref: ForwardedRef<WidgetFormRef>,
) {
  if (content.type !== WidgetType.ADD_PAGE) {
    throw new Error('Invalid widget content type');
  }

  const defaultContent = content.content;

  const [formState, setFormState] = useState<{
    query?: string;
    journal: {
      id: string;
      title: string;
    } | null;
    cursor?: string;
  }>({
    journal: null,
  });

  const [intersectionRef, entry] = useIntersectionObserver({
    threshold: 0.1,
    root: null,
    rootMargin: '0px',
  });

  const {
    data: journalPages,
    hasNextPage,
    fetchNextPage,
  } = useGetJournalsQuery({
    query: formState.query,
  });

  const formRef = useRef<HTMLFormElement>(null);
  const form = useForm<AddPageWidgetFormValues>({
    resolver: zodResolver(AddPageWidgetFormSchema),
    defaultValues: {
      journalId:
        content.type === WidgetType.ADD_PAGE ? defaultContent.journalId : '',
    },
  });

  useEffect(() => {
    if (entry?.isIntersecting && hasNextPage) {
      fetchNextPage();
    }
  }, [entry, hasNextPage, fetchNextPage]);

  useImperativeHandle(ref, () => ({
    submit() {
      form.handleSubmit(formSubmitHandler)();
    },
  }));

  const formSubmitHandler = ({ journalId }: AddPageWidgetFormValues) => {
    onSubmit({
      type: WidgetType.ADD_PAGE,
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
            <FormItem className='flex'>
              <FormLabel>Journal ID</FormLabel>

              <FormControl />

              <Popover modal>
                <PopoverTrigger asChild>
                  <Button variant='outline' className='grow'>
                    {formState.journal
                      ? formState.journal.title
                      : 'Select Journal'}
                  </Button>
                </PopoverTrigger>

                <PopoverContent>
                  <Command>
                    <CommandInput />
                    <CommandList>
                      <CommandEmpty>No journals found.</CommandEmpty>

                      <CommandGroup>
                        <ScrollArea className='w-full h-24'>
                          {journalPages?.pages &&
                            journalPages.pages.map((journal) =>
                              journal.journals.map((j) => (
                                <CommandItem
                                  key={j.id}
                                  selected={field.value === j.id}
                                  onSelect={() => {
                                    field.onChange(j.id);
                                    setFormState((prev) => ({
                                      ...prev,
                                      journal: {
                                        id: j.id,
                                        title: j.title,
                                      },
                                    }));
                                  }}
                                >
                                  {j.title}
                                </CommandItem>
                              )),
                            )}
                          {<div ref={intersectionRef}></div>}
                        </ScrollArea>
                      </CommandGroup>
                    </CommandList>
                  </Command>
                </PopoverContent>
              </Popover>
            </FormItem>
          )}
        />
      </form>
    </Form>
  );
});

async function getAddPageWidgetDefaultContent(): Promise<WidgetContent> {
  return {
    type: WidgetType.ADD_PAGE,
    content: {
      journalId: '',
    },
  };
}

export default AddPageWidget;
export { AddPageWidgetConfigForm, getAddPageWidgetDefaultContent };
