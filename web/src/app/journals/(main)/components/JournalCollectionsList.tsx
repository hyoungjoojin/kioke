'use client';

import Sidebar from './Sidebar';
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
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
import { IconName } from '@/components/ui/icon';
import { Input } from '@/components/ui/input';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { JournalType } from '@/constant/journal';
import {
  useCollectionsQuery,
  useCreateCollectionMutationQuery,
} from '@/query/collection';
import { useCreateJournalMutation } from '@/query/journal';
import type { Collection } from '@/types/collection';
import { zodResolver } from '@hookform/resolvers/zod';
import { AnimatePresence, motion } from 'motion/react';
import { useTranslations } from 'next-intl';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import z from 'zod';

interface SidebarState {
  open: boolean;
  selectedCollection: Collection | null;
}

export default function JournalCollectionsList() {
  const t = useTranslations();

  const { data: collections } = useCollectionsQuery();
  const {
    mutate: createJournalCollection,
    isPending: isCreateJournalCollectionPending,
  } = useCreateCollectionMutationQuery();

  const [sidebar, setSidebar] = useState<SidebarState>({
    open: false,
    selectedCollection: null,
  });

  if (!collections) {
    return null;
  }

  const onCreateJournalCollectionButtonClick = () => {
    createJournalCollection({
      name: t('default-values.initial-journal-collection'),
    });
  };

  return (
    <div className='flex flex-col'>
      <div className='self-end mb-10 flex gap-2'>
        <Button
          icon={IconName.PLUS}
          pending={isCreateJournalCollectionPending}
          onClick={onCreateJournalCollectionButtonClick}
        >
          Add Collection
        </Button>

        <AddNewJournalDialog />
      </div>
      <div className='flex justify-between'>
        <div>
          {collections.map((collection, index) => {
            return (
              <div
                onClick={() => {
                  setSidebar(() => ({
                    open: true,
                    selectedCollection: collection,
                  }));
                }}
                className='hover:underline hover:cursor-pointer mb-5'
                key={index}
              >
                {collection.name}
              </div>
            );
          })}
        </div>

        <AnimatePresence>
          {sidebar.open && sidebar.selectedCollection && (
            <motion.div
              initial={{ opacity: 0, scale: 1, x: 20 }}
              animate={{ opacity: 1, scale: 1, x: 0 }}
              exit={{ opacity: 0, scale: 1, x: 20 }}
              transition={{ type: 'spring', stiffness: 300, damping: 30 }}
            >
              <Sidebar
                collectionId={sidebar.selectedCollection.id}
                onClose={() => {
                  setSidebar((sidebar) => ({
                    ...sidebar,
                    open: false,
                  }));
                }}
              />
            </motion.div>
          )}
        </AnimatePresence>
      </div>
    </div>
  );
}

const JournalFormSchema = z.object({
  collection: z.string(),
  type: z.enum(JournalType),
  title: z.string(),
});
type JournalFormSchema = z.infer<typeof JournalFormSchema>;

function AddNewJournalDialog() {
  const journalForm = useForm<JournalFormSchema>({
    resolver: zodResolver(JournalFormSchema),
    defaultValues: {
      collection: '',
      type: JournalType.BASIC,
      title: 'Untitled',
    },
  });

  const { data: collections } = useCollectionsQuery();
  const { mutate: createJournal, isPending: isCreateJournalPending } =
    useCreateJournalMutation();

  const journalFormSubmitHandler = async (values: JournalFormSchema) => {
    createJournal({
      collectionId: values.collection,
      type: values.type,
      title: values.title,
    });
  };

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button icon={IconName.EDIT}>Write New Journal</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Add new journal</DialogTitle>
          <DialogDescription>Write a new journal</DialogDescription>
        </DialogHeader>

        <Form {...journalForm}>
          <form onSubmit={journalForm.handleSubmit(journalFormSubmitHandler)}>
            <div className='flex flex-col gap-5'>
              <FormField
                name='collection'
                render={({ field }) => {
                  return (
                    <FormItem>
                      <FormLabel>Select Collection</FormLabel>
                      <Select
                        onValueChange={field.onChange}
                        defaultValue={field.value}
                        {...field}
                      >
                        <FormControl>
                          <SelectTrigger>
                            <SelectValue placeholder='Select a journal collection' />
                          </SelectTrigger>
                        </FormControl>

                        {collections ? (
                          <SelectContent>
                            {collections.map((collection, index) => {
                              return (
                                <SelectItem value={collection.id} key={index}>
                                  {collection.name}
                                </SelectItem>
                              );
                            })}
                          </SelectContent>
                        ) : null}
                      </Select>
                    </FormItem>
                  );
                }}
              />

              <FormField
                name='type'
                render={({ field }) => {
                  return (
                    <FormItem>
                      <FormLabel>Select Journal Type</FormLabel>
                      <Select
                        onValueChange={field.onChange}
                        defaultValue={field.value}
                        {...field}
                      >
                        <FormControl>
                          <SelectTrigger>
                            <SelectValue placeholder='Select the type of the journal' />
                          </SelectTrigger>
                        </FormControl>

                        <SelectContent>
                          {Object.keys(JournalType).map((type, index) => {
                            return (
                              <SelectItem value={type} key={index}>
                                {type}
                              </SelectItem>
                            );
                          })}
                        </SelectContent>
                      </Select>
                    </FormItem>
                  );
                }}
              />

              <FormField
                name='title'
                render={({ field }) => {
                  return (
                    <FormItem>
                      <FormLabel>Journal Title</FormLabel>
                      <FormControl>
                        <Input
                          type='text'
                          placeholder='Journal Title'
                          {...field}
                        />
                      </FormControl>
                    </FormItem>
                  );
                }}
              />

              <DialogFooter>
                <DialogClose asChild>
                  <Button variant='outline'>Cancel</Button>
                </DialogClose>
                <Button type='submit' pending={isCreateJournalPending}>
                  Save changes
                </Button>
              </DialogFooter>
            </div>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
}
