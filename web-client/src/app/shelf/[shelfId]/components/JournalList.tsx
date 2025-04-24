"use client";

import {
  Table,
  TableBody,
  TableHead,
  TableHeader,
  TableRow,
  TableCell,
} from "@/components/ui/table";
import { Input } from "@/components/ui/input";
import { useRouter } from "next/navigation";
import View from "@/constants/view";
import {
  Ellipsis,
  Trash2,
  SquareArrowRight,
  Heart,
  SquarePen,
} from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuGroup,
  DropdownMenuSub,
  DropdownMenuSubTrigger,
  DropdownMenuSubContent,
  DropdownMenuPortal,
  DropdownMenuTrigger,
  DropdownMenuSeparator,
} from "@/components/ui/dropdown-menu";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from "@/components/ui/form";
import { Separator } from "@/components/ui/separator";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogTitle,
  DialogFooter,
  DialogHeader,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { JSX, useState } from "react";
import {
  useMoveJournalMutation,
  useDeleteJournalMutation,
  useToggleJournalBookmarkMutation,
  useCreateJournalMutation,
} from "@/hooks/query/journal";
import SelectShelfCommand from "@/components/features/shelf/SelectShelfCommand";
import { JournalPreview } from "@/types/primitives/journal";
import { KIOKE_ROUTES } from "@/constants/route";
import z from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { cn } from "@/lib/utils";

enum ModalType {
  DELETE_JOURNAL = "delete",
}

type JournalListItemProps = JournalPreview & {
  isArchived: boolean;
};

const JournalListItem = ({
  journalId,
  title,
  createdAt,
  bookmarked,
  isArchived,
}: JournalListItemProps) => {
  const router = useRouter();

  const { mutate: moveJournal } = useMoveJournalMutation(journalId);
  const { mutate: deleteJournal } = useDeleteJournalMutation(journalId);
  const { mutate: toggleJournalBookmark } =
    useToggleJournalBookmarkMutation(journalId);

  const JournalListItemMenu = () => {
    const [modalState, setModalState] = useState<{
      open: boolean;
      modal: ModalType | null;
    }>({
      open: false,
      modal: null,
    });

    const openModal = (modal: ModalType) => {
      setModalState(() => ({ open: true, modal }));
    };

    const closeModal = () => {
      setModalState(() => ({ open: false, modal: null }));
    };

    const modals: { [key in ModalType]: JSX.Element } = {
      [ModalType.DELETE_JOURNAL]: (
        <DialogContent onEscapeKeyDown={closeModal} hideCloseButton>
          <DialogHeader>
            <DialogTitle>Delete journal?</DialogTitle>
            {isArchived ? (
              <DialogDescription>
                Journal <span className="italic">{title}</span> and its pages
                will be permanently deleted.
              </DialogDescription>
            ) : (
              <DialogDescription>
                Journal <span className="italic">{title}</span> and its pages
                will be moved to the archive.
              </DialogDescription>
            )}
          </DialogHeader>

          <DialogFooter>
            <Button onClick={closeModal}>Cancel</Button>
            <Button
              type="submit"
              variant="destructive"
              onClick={async () => {
                deleteJournal();
                closeModal();
              }}
            >
              Delete
            </Button>
          </DialogFooter>
        </DialogContent>
      ),
    };

    return (
      <Dialog open={modalState.open} onOpenChange={closeModal}>
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <div>
              <Ellipsis size={15} />
            </div>
          </DropdownMenuTrigger>

          <DropdownMenuContent align="center">
            <DropdownMenuGroup>
              <DropdownMenuSub>
                <DropdownMenuSubTrigger onSelect={() => {}}>
                  <div className="flex items-center">
                    <SquareArrowRight size={16} className="mx-1" />
                    Move to
                  </div>
                </DropdownMenuSubTrigger>
                <DropdownMenuPortal>
                  <DropdownMenuSubContent>
                    <SelectShelfCommand
                      onSelect={(shelf) => {
                        moveJournal({ shelfId: shelf.shelfId });
                      }}
                    />
                  </DropdownMenuSubContent>
                </DropdownMenuPortal>
              </DropdownMenuSub>
            </DropdownMenuGroup>

            <DropdownMenuSeparator />

            <DropdownMenuItem
              onSelect={() => openModal(ModalType.DELETE_JOURNAL)}
            >
              <div className="flex items-center text-red-500">
                <Trash2 size={16} className="mx-1" />
                Delete Journal
              </div>
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>

        {modalState.open && modalState.modal && modals[modalState.modal]}
      </Dialog>
    );
  };

  return (
    <TableRow
      changeOnHover
      onClick={() => {
        router.push(KIOKE_ROUTES[View.JOURNAL_PREVIEW](journalId));
      }}
    >
      <TableCell>
        <p className="select-none">{title}</p>
      </TableCell>

      <TableCell>
        <p className="select-none">{createdAt}</p>
      </TableCell>

      <TableCell>
        <div className="flex justify-start items-center">
          <p
            onClick={(e) => {
              toggleJournalBookmark(!bookmarked);
              e.stopPropagation();
            }}
            className="select-none mr-3"
          >
            {bookmarked ? (
              <Heart size={20} fill="black" />
            ) : (
              <Heart size={20} />
            )}
          </p>

          <JournalListItemMenu />
        </div>
      </TableCell>
    </TableRow>
  );
};

const CreateJournalFormSchema = z.object({
  title: z.string().nonempty(),
  description: z.string().nonempty(),
});

function CreateJournalButton({ shelfId }: { shelfId: string }) {
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

    createJournal({
      shelfId,
      title,
      description,
    });
  };

  return (
    <Dialog>
      <DialogTrigger
        className={cn(
          "inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:pointer-events-none disabled:opacity-50 [&_svg]:pointer-events-none",
          "hover:bg-accent hover:text-accent-foreground text-black",
          "h-9 px-1 py-2",
        )}
      >
        <SquarePen size={16} />
        <span>Add Journal</span>
      </DialogTrigger>

      <DialogContent className="h-80">
        <DialogHeader>
          <DialogTitle>Add journal</DialogTitle>
          <DialogDescription>
            Add a new journal to your shelf.
          </DialogDescription>
        </DialogHeader>

        <Separator className="my-2" />

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
                <FormItem className="my-2">
                  <FormLabel>Description</FormLabel>
                  <FormControl>
                    <Input placeholder="" {...field} />
                  </FormControl>
                </FormItem>
              )}
            />

            <div className="flex justify-end">
              <Button type="submit">Add</Button>
            </div>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
}

export default function JournalList({
  journals,
  shelfId,
  isArchived,
}: {
  journals: JournalPreview[];
  shelfId: string;
  isArchived: boolean;
}) {
  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead className="select-none">Title</TableHead>

          <TableHead className="select-none">Created At</TableHead>

          <TableHead className="select-none">
            {!isArchived && <CreateJournalButton shelfId={shelfId} />}
          </TableHead>
        </TableRow>
      </TableHeader>

      <TableBody>
        {journals.map((journal, index) => {
          return (
            <JournalListItem key={index} {...journal} isArchived={isArchived} />
          );
        })}
      </TableBody>
    </Table>
  );
}
