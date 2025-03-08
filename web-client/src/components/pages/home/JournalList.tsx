"use client";

import {
  Table,
  TableBody,
  TableHead,
  TableHeader,
  TableRow,
  TableCell,
} from "@/components/ui/table";
import { useRouter } from "next/navigation";
import { useSelectedShelf } from "@/hooks/store";
import { Ellipsis, Trash2, SquareArrowRight } from "lucide-react";
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
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogTitle,
} from "@/components/ui/dialog";
import { DialogFooter, DialogHeader } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { JSX, useState } from "react";
import {
  useMoveJournalMutation,
  useDeleteJournalMutation,
} from "@/hooks/query/journal";
import { useShelvesQuery } from "@/hooks/query/shelf";

enum ModalType {
  DELETE_JOURNAL = "delete",
}

interface JournalListItemProps {
  jid: string;
  title: string;
}

const JournalListItem = ({ jid, title }: JournalListItemProps) => {
  const router = useRouter();

  const { mutate: moveJournal } = useMoveJournalMutation(jid);
  const { mutate: deleteJournal } = useDeleteJournalMutation(jid);
  const { data } = useShelvesQuery();

  const shelves = data?.shelves;
  const selectedShelf = useSelectedShelf(shelves);

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
            {selectedShelf?.isArchive ? (
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
                    <Command
                      onClick={(e) => {
                        e.stopPropagation();
                      }}
                    >
                      <CommandInput
                        autoFocus
                        placeholder="Enter a shelf to move to"
                      />
                      <CommandList>
                        <CommandGroup>
                          <CommandEmpty>No results found.</CommandEmpty>
                          {shelves &&
                            shelves.map((shelf, index) => {
                              if (shelf.isArchive) {
                                return null;
                              }

                              return (
                                <CommandItem
                                  key={index}
                                  onSelect={() => {
                                    moveJournal({ shelfId: shelf.id });
                                  }}
                                >
                                  {shelf.name}
                                </CommandItem>
                              );
                            })}
                        </CommandGroup>
                      </CommandList>
                    </Command>
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
        router.push(`/journal/${jid}/preview`);
      }}
    >
      <TableCell>
        <p className="select-none">{title}</p>
      </TableCell>

      <TableCell>
        <p className="select-none">{jid}</p>
      </TableCell>

      <TableCell
        onClick={(e) => {
          e.stopPropagation();
        }}
      >
        <JournalListItemMenu />
      </TableCell>
    </TableRow>
  );
};

export default function JournalList() {
  const { data, isLoading, isError } = useShelvesQuery();
  const selectedShelf = useSelectedShelf(data?.shelves);

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead className="select-none">Title</TableHead>
          <TableHead className="select-none">Description</TableHead>
          <TableHead></TableHead>
        </TableRow>
      </TableHeader>

      <TableBody>
        {!isLoading && !isError && selectedShelf
          ? selectedShelf.journals.map((journal, index) => {
              return (
                <JournalListItem
                  key={index}
                  jid={journal.id}
                  title={journal.title}
                />
              );
            })
          : null}
      </TableBody>
    </Table>
  );
}
