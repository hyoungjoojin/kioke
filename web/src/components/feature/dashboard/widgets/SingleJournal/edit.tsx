import type { SingleJournalWidgetContent } from '.';
import type { DashboardWidgetEditModalProps } from '@/components/feature/dashboard/widgets';
import { Button } from '@/components/ui/button';
import {
  DialogClose,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from '@/components/ui/form';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { useCollectionsQuery } from '@/query/collection';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import z from 'zod';

const FormSchema = z.object({
  collectionId: z.string(),
  journalId: z.string(),
});
type FormSchemaType = z.infer<typeof FormSchema>;

export default function SingleJournalWidgetEditModal({
  content,
  onSubmit,
}: DashboardWidgetEditModalProps & {
  content: SingleJournalWidgetContent;
}) {
  const { data: collections } = useCollectionsQuery();

  const form = useForm<FormSchemaType>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      journalId: content.journalId,
    },
  });

  const onFormSubmit = (values: FormSchemaType) => {
    onSubmit({
      journalId: values.journalId,
    } as SingleJournalWidgetContent);
  };

  return (
    <>
      <DialogHeader>
        <DialogTitle>Select Journal</DialogTitle>
        <DialogDescription>Display a journal</DialogDescription>

        <Form {...form}>
          <form onSubmit={form.handleSubmit(onFormSubmit)}>
            <FormField
              control={form.control}
              name='collectionId'
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Collection</FormLabel>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <FormControl>
                      <SelectTrigger>
                        <SelectValue placeholder='Select a journal collection' />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      {collections &&
                        collections.map((collection, index) => {
                          return (
                            <SelectItem value={collection.id} key={index}>
                              {collection.name}
                            </SelectItem>
                          );
                        })}
                    </SelectContent>
                  </Select>
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name='journalId'
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Journal</FormLabel>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <FormControl>
                      <SelectTrigger>
                        <SelectValue placeholder='Select a journal' />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      {collections
                        ? collections
                            .find(
                              (collection) =>
                                collection.id === form.getValues().collectionId,
                            )
                            ?.journals.map((journal, index) => {
                              return (
                                <SelectItem value={journal.id} key={index}>
                                  {journal.title}
                                </SelectItem>
                              );
                            }) || <div>Select a collection first</div>
                        : null}
                    </SelectContent>
                  </Select>
                </FormItem>
              )}
            />

            <DialogFooter>
              <DialogClose asChild>
                <Button variant='outline'>Cancel</Button>
              </DialogClose>
              <Button type='submit'>Save changes</Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogHeader>
    </>
  );
}
