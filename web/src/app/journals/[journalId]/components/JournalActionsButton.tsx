import { uploadImage } from '@/app/api/image/uploadImage';
import { updateJournal } from '@/app/api/journal';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuPortal,
  DropdownMenuSub,
  DropdownMenuSubContent,
  DropdownMenuSubTrigger,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { FileInput } from '@/components/ui/input';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';
import { Spinner } from '@/components/ui/spinner';
import { JournalType } from '@/constant/journal';
import logger from '@/lib/logger';
import { useJournalQuery, useUpdateJournalMutation } from '@/query/journal';
import { unwrap } from '@/util/result';
import { useClickAway } from '@uidotdev/usehooks';
import { type ChangeEvent, useState } from 'react';
import { toast } from 'sonner';

interface JournalActionsButtonProps {
  journalId: string;
}

export default function JournalActionsButton({
  journalId,
}: JournalActionsButtonProps) {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant='ghost' />
      </DropdownMenuTrigger>

      <DropdownMenuContent align='end' sideOffset={10}>
        <ChangeTypeButton journalId={journalId} />
        <UpdateCoverButton journalId={journalId} />
        <DropdownMenuItem>Delete Journal</DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}

function ChangeTypeButton({ journalId }: { journalId: string }) {
  const { data: journal } = useJournalQuery({ journalId });
  const { mutate: updateJournal, isPending: isUpdateJournalPending } =
    useUpdateJournalMutation({ id: journalId });

  const [selected, setSelected] = useState(JournalType.BASIC);

  const itemSelectHandler = (type: JournalType) => {
    if (!journal || type === journal.type) {
      return;
    }

    setSelected(type);
    updateJournal({
      type,
    });
  };

  return (
    <DropdownMenuSub>
      <DropdownMenuSubTrigger>Change Type</DropdownMenuSubTrigger>
      <DropdownMenuPortal>
        <DropdownMenuSubContent>
          {journal &&
            Object.values(JournalType).map((type, index) => {
              return (
                <DropdownMenuItem
                  key={index}
                  onSelect={() => itemSelectHandler(type)}
                  className='flex justify-between items-center'
                >
                  <span>{type}</span>

                  {journal.type === type ? (
                    <span></span>
                  ) : isUpdateJournalPending && journal.type === selected ? (
                    <span>
                      <Spinner />
                    </span>
                  ) : null}
                </DropdownMenuItem>
              );
            })}
        </DropdownMenuSubContent>
      </DropdownMenuPortal>
    </DropdownMenuSub>
  );
}

function UpdateCoverButton({ journalId }: { journalId: string }) {
  const [isPopoverOpen, setIsPopoverOpen] = useState(false);
  const [isFileUploadPending, setIsFileUploadPending] = useState(false);

  const ref = useClickAway<HTMLDivElement>(() => {
    setIsPopoverOpen(false);
  });

  const fileUploadHandler = async (e: ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;
    if (!files || files.length === 0) {
      toast.error('No image selected');
      setIsPopoverOpen(false);
      return;
    }

    setIsFileUploadPending(true);

    const file = files[0];
    const response = await uploadImage({
      name: file.name,
      contentType: file.type,
      contentLength: file.size,
    })
      .then((response) => unwrap(response))
      .catch((error) => {
        logger.debug(error);
      });

    if (!response) {
      toast.error('Internal server error');
      setIsFileUploadPending(false);
      return;
    }

    await fetch(response.signedPostUrl, {
      method: 'PUT',
      body: file,
      headers: {
        'Content-Type': file.type,
      },
    }).catch((error) => {
      logger.debug(error);
      toast.error('Internal server error');
    });

    await updateJournal(
      { id: journalId },
      {
        coverImage: response.imageId,
      },
    )
      .then((response) => unwrap(response))
      .catch((error) => {
        logger.debug(error);
        toast.error('Internal server error');
      });

    setIsPopoverOpen(false);
  };

  return (
    <>
      <DropdownMenuItem
        icon={IconName.IMAGE}
        onSelect={(e) => {
          e.preventDefault();
          setIsPopoverOpen(true);
        }}
      >
        Update Cover
      </DropdownMenuItem>

      <Popover open={isPopoverOpen}>
        <PopoverTrigger
          asChild
          onClick={() => {
            setIsPopoverOpen(true);
          }}
        >
          <span />
        </PopoverTrigger>
        <PopoverContent ref={ref} side='right' className='mx-1'>
          <span>Update Journal Cover</span>
          <span></span>

          <FileInput
            pending={isFileUploadPending}
            onChange={fileUploadHandler}
            className='border rounded-xl my-2 py-2'
          >
            Upload Image
          </FileInput>
        </PopoverContent>
      </Popover>
    </>
  );
}
