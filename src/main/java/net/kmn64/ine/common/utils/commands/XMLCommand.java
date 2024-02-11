package net.kmn64.ine.common.utils.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class XMLCommand {
	public static LiteralArgumentBuilder<CommandSource> create(){
		LiteralArgumentBuilder<CommandSource> lab = Commands.literal("xml");
		lab.then(Commands.literal("beginTest").executes(source->{
			beginTest(source);
			return Command.SINGLE_SUCCESS;
		}));
		
		return lab;
	}

	private static void beginTest(CommandContext<CommandSource> source) {
		source.getSource().sendSuccess(new StringTextComponent("[Test Writing]Attempt to Write XML file."), false);
	}
}
