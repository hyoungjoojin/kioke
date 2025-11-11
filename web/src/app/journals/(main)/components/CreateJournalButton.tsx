import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import useCreateJournalMutation from '@/hooks/query/useCreateJournalMutation';
import useGetCollectionsQuery from '@/hooks/query/useGetCollections';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import z from 'zod';

const CreateJournalFormSchema = z.object({
  title: z.string().min(1, 'Journal name is required'),
  collectionId: z.string().nonempty(),
});
type CreateJournalFormSchemaType = z.infer<typeof CreateJournalFormSchema>;

function CreateJournalButton() {
  const { data: collections } = useGetCollectionsQuery();
  const { mutate: createJournal } = useCreateJournalMutation();

  const createJournalForm = useForm<CreateJournalFormSchemaType>({
    resolver: zodResolver(CreateJournalFormSchema),
    defaultValues: {
      title: '',
      collectionId: '',
    },
  });

  const createJournalFormSubmitHandler = (
    data: CreateJournalFormSchemaType,
  ) => {
    createJournal({
      body: {
        collectionId: data.collectionId,
        title: data.title,
      },
    });
  };

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant='icon' icon='plus' />
      </DialogTrigger>
      <DialogContent>
        <DialogTitle>Add journal</DialogTitle>

        <Form {...createJournalForm}>
          <form
            onSubmit={createJournalForm.handleSubmit(
              createJournalFormSubmitHandler,
            )}
            className='flex flex-col gap-5'
          >
            <div>
              <FormField
                name='title'
                control={createJournalForm.control}
                render={({ field }) => {
                  return (
                    <FormItem>
                      <FormLabel>Title</FormLabel>
                      <FormControl>
                        <Input type='text' {...field} />
                      </FormControl>
                    </FormItem>
                  );
                }}
              />
            </div>

            <div>
              <FormField
                name='collectionId'
                control={createJournalForm.control}
                render={({ field }) => {
                  return (
                    <FormItem>
                      <FormLabel>Collection</FormLabel>

                      <Select
                        onValueChange={field.onChange}
                        defaultValue={field.value}
                        {...field}
                      >
                        <FormControl>
                          <SelectTrigger className='w-full'>
                            <SelectValue placeholder='Select a collection' />
                          </SelectTrigger>
                        </FormControl>

                        <SelectContent>
                          {collections &&
                            collections.map((collection, index) => (
                              <SelectItem
                                key={index}
                                value={collection.id}
                                className='flex items-center gap-2'
                              >
                                <span>{collection.name}</span>

                                {collection.isDefault && (
                                  <Badge variant='outline'>Default</Badge>
                                )}
                              </SelectItem>
                            ))}
                        </SelectContent>
                      </Select>
                    </FormItem>
                  );
                }}
              />
            </div>

            <div className='flex justify-end gap-2'>
              <Button variant='ghost'>Cancel</Button>
              <Button type='submit'>Add</Button>
            </div>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
}

export default CreateJournalButton;
