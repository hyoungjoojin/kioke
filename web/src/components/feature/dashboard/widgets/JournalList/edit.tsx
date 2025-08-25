import { Button } from '@/components/ui/button';
import {
  DialogDescription,
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
  SelectValue,
} from '@/components/ui/select';
import { useCollectionsQuery } from '@/query/collection';
import { zodResolver } from '@hookform/resolvers/zod';
import { SelectTrigger } from '@radix-ui/react-select';
import { useForm } from 'react-hook-form';
import z from 'zod';

const FormSchema = z.object({
  collection: z.string(),
});
type FormSchemaType = z.infer<typeof FormSchema>;

export default function JournalListEdit() {
  const { data: collections } = useCollectionsQuery();

  const form = useForm<FormSchemaType>({
    resolver: zodResolver(FormSchema),
  });

  function onFormSubmit(_: FormSchemaType) {}

  return (
    <>
      <DialogHeader>
        <DialogTitle>Journal List Widget</DialogTitle>
        <DialogDescription>
          Displays the journals inside a journal collection
        </DialogDescription>
      </DialogHeader>

      <Form {...form}>
        <form onSubmit={form.handleSubmit(onFormSubmit)}>
          <FormField
            control={form.control}
            name='collection'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Journal Collection</FormLabel>
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

          <Button type='submit'>Submit</Button>
        </form>
      </Form>
    </>
  );
}
