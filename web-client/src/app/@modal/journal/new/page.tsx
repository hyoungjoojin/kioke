"use client";

import Modal from "@/components/ui/modal";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from "@/components/ui/form";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "@/components/ui/button";
import { useRouter } from "next/navigation";
import { useShelvesQuery } from "@/hooks/query/shelf";
import { useSelectedShelf } from "@/hooks/store";
import { useCreateJournalMutation } from "@/hooks/query/journal";

const CreateJournalFormSchema = z.object({
  title: z.string().nonempty(),
  description: z.string().nonempty(),
});

export default function CreateJournalModal() {
  const router = useRouter();

  const { data: shelves } = useShelvesQuery();
  const selectedShelf = useSelectedShelf(shelves);
  const { mutate: createJournal } = useCreateJournalMutation();

  const createJournalForm = useForm<z.infer<typeof CreateJournalFormSchema>>({
    resolver: zodResolver(CreateJournalFormSchema),
    defaultValues: {
      title: "",
      description: "",
    },
  });

  const formSubmitHandler = async (
    values: z.infer<typeof CreateJournalFormSchema>,
  ) => {
    const { title, description } = values;

    if (selectedShelf) {
      createJournal({
        shelfId: selectedShelf.id,
        title,
        description,
      });
    }

    router.back();
  };

  return (
    <Modal title="Add journal" variant="sm">
      <Form {...createJournalForm}>
        <form onSubmit={createJournalForm.handleSubmit(formSubmitHandler)}>
          <FormField
            name="title"
            control={createJournalForm.control}
            render={({ field }) => (
              <FormItem>
                <FormLabel>Title</FormLabel>
                <FormControl>
                  <Input placeholder="" {...field} />
                </FormControl>
              </FormItem>
            )}
          />

          <FormField
            name="description"
            control={createJournalForm.control}
            render={({ field }) => (
              <FormItem className="my-5">
                <FormLabel>Description</FormLabel>
                <FormControl>
                  <Input placeholder="" {...field} />
                </FormControl>
              </FormItem>
            )}
          />

          <div className="flex absolute bottom-10 right-10 justify-center">
            <Button type="submit" className="w-full">
              Add
            </Button>
          </div>
        </form>
      </Form>
    </Modal>
  );
}
