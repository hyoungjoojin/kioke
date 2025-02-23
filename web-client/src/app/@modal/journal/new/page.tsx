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
import { createJournal } from "@/app/api/journal";
import { useRouter } from "next/navigation";
import { useShelvesQuery } from "@/hooks/query";
import { getQueryClient } from "@/components/providers/QueryProvider";
import { getShelves } from "@/app/api/shelf";
import { useSelectedShelf } from "@/hooks/store";

const CreateJournalFormSchema = z.object({
  title: z.string().nonempty(),
});

export default function CreateJournalModal() {
  const router = useRouter();

  const queryClient = getQueryClient();

  const { data } = useShelvesQuery();
  const selectedShelf = useSelectedShelf(data?.shelves);

  const createJournalForm = useForm<z.infer<typeof CreateJournalFormSchema>>({
    resolver: zodResolver(CreateJournalFormSchema),
    defaultValues: {
      title: "",
    },
  });

  const formSubmitHandler = async (
    values: z.infer<typeof CreateJournalFormSchema>,
  ) => {
    const { title } = values;

    if (selectedShelf) {
      await createJournal(title, selectedShelf.id);
    }

    queryClient.invalidateQueries({
      queryKey: ["shelves"],
      queryFn: getShelves,
    });

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
