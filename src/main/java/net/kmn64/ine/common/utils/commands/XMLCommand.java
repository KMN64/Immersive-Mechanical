package net.kmn64.ine.common.utils.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.kmn64.ine.api.crafting.xml.XMLWriter;
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
		source.getSource().sendSuccess(new StringTextComponent("[Writing Test]Attempt to Write XML file."), false);
		boolean isSuccess = XMLWriter.writingTest();
		if(!isSuccess)
		{
			source.getSource().sendFailure(new StringTextComponent("[Writing Test]Failed to Write XML file,check inexml.log to detail information"));
			return;
		}
		
	}
}
